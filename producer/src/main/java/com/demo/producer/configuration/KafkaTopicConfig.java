package com.demo.producer.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic createMyTopic() {
        return new NewTopic("my-new-topic-java", 3, (short) 1);
    }
}
