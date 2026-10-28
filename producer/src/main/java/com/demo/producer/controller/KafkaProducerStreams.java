package com.demo.producer.controller;


import com.demo.producer.entity.RiderLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class KafkaProducerStreams {

    @Bean
    public Supplier<RiderLocation> sendRiderLocation() {
        Random random = new Random();
        return () -> {

            String riderId = "rider" + random.nextInt(2000);
            RiderLocation location = new RiderLocation(riderId,
                    28.61,
                    77.22);
            System.out.println("Sending: " + location.getRiderId());
            return location;
        };
    }

    @Bean
    public Supplier<Message<String>> sendRiderStatus() {
        Random random = new Random();
        return () -> {
            String riderId = "rider" + random.nextInt(2000);
            String status = random.nextBoolean() ? "rideStarted"
                    : "rideCompleted";
            System.out.println("Sending: " + status);
            return MessageBuilder.withPayload(riderId + ": " + status)
                    .setHeader(KafkaHeaders.KEY, riderId.getBytes())
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
                    .build();
        };
    }
}
