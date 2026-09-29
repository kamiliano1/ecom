package com.ecommerce.order.clients;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
public class ProductServiceClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public ProductServiceClient webClientHttpInterface(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder
                .baseUrl("http://product-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.empty())
                .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();
        return factory.createClient(ProductServiceClient.class);
    }
}
