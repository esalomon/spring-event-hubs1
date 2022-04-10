package com.example.event.hub;

import com.azure.spring.messaging.checkpoint.Checkpointer;
import com.azure.spring.messaging.eventhubs.support.EventHubsHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.azure.spring.messaging.AzureHeaders.CHECKPOINTER;

@Slf4j
@Configuration
public class EventHubConsumer {

	@Bean
	public Consumer<Message<String>> consume() {
		return message -> {
			Checkpointer checkpointer = (Checkpointer) message.getHeaders().get(CHECKPOINTER);
			log.info("New message received: '{}', partition key: {}, sequence number: {}, offset: {}, enqueued time: {}",
					message.getPayload(),
					message.getHeaders().get(EventHubsHeaders.PARTITION_KEY),
					message.getHeaders().get(EventHubsHeaders.SEQUENCE_NUMBER),
					message.getHeaders().get(EventHubsHeaders.OFFSET),
					message.getHeaders().get(EventHubsHeaders.ENQUEUED_TIME)
			);
			checkpointer.success()
					.doOnSuccess(success -> log.info("Message '{}' successfully checkpointed", message.getPayload()))
					.doOnError(error -> log.error("Exception found", error))
					.block();
		};
	}
}
