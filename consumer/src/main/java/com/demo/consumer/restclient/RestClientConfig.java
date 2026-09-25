package com.demo.consumer.restclient;

import org.springframework.boot.restclient.autoconfigure.RestClientBuilderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    // Definiujemy pojedynczy bean RestClient, współdzielony przez całą aplikację.
    // Celowo NIE ustawiamy tu baseUrl ani @LoadBalanced - adres docelowy
    // (konkretny host:port konkretnej instancji) ustalamy ręcznie w miejscu wywołania,
    // po to żeby uniknąć buga w Spring Cloud, gdzie Eureka "przejmuje" load-balanced
    // RestClient.Builder do własnej rejestracji i się wywraca.
    @Bean
    public RestClient restClient(RestClientBuilderConfigurer configurer) {
        // RestClientBuilderConfigurer to obiekt dostarczany automatycznie przez Spring Boota.
        // Dokleja do buildera standardowe usprawnienia Boota (np. obsługę message converterów,
        // observability/metrics), które inaczej trzeba by było konfigurować ręcznie.
        return configurer.configure(RestClient.builder())
                .build(); // budujemy gotowy, niemodyfikowalny obiekt RestClient
    }
}
