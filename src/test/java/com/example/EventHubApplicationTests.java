package com.example;

import com.example.controller.EventProducerController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventHubApplicationTests {

	@Test //Source: https://www.jvt.me/posts/2021/06/25/spring-context-test/
	void contextLoads(ApplicationContext context) {

		assertThat(context).isNotNull();
	}

	@Test
	void beansAreLoaded(ApplicationContext context) {

		assertThat(context.getBean(EventProducerController.class)).isNotNull();
		assertThat(context.getBean("consume")).isNotNull();
		assertThat(context.getBean("many")).isNotNull();
		assertThat(context.getBean("supply")).isNotNull();
	}
}
