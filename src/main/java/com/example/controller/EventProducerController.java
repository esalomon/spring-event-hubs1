package com.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Sinks;

@Slf4j
@RestController
@AllArgsConstructor
public class EventProducerController {

    private Sinks.Many<Message<String>> many;

    @PostMapping("/messages")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {

        log.info("Going to add message {} to event hub.", message);
        many.emitNext(MessageBuilder.withPayload(message).build(),
                      Sinks.EmitFailureHandler.FAIL_FAST);
        return ResponseEntity.ok("The following message was sent: " + message);
    }
}
