package com.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class EmailServiceApplication {
	private final Logger logger = LoggerFactory.getLogger(EmailServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}


	@KafkaListener(topics = "${app.kafka.topic-name}")
	public void consumeEmailEvent(String event) {
		logger.info("Event received {}", event);
	}
}
