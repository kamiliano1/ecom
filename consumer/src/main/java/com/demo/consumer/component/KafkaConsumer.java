package com.demo.consumer.component;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "my-topic", groupId = "my-new-group")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}
