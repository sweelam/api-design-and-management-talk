package com.flight.booking.service;

import com.flight.booking.builders.EmailBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;

    public EmailService(KafkaTemplate<String, String> kafkaTemplate,
                        @Value("${app.kafka.topic-name}") final String topicName) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    public void sendEmail(String customerEmail, String flightName) {
        kafkaTemplate.send(topicName, EmailBuilders.buildEmailEvent(customerEmail, flightName));
    }
}
