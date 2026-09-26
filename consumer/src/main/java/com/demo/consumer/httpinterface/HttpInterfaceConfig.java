package com.demo.consumer.httpinterface;

import org.springframework.boot.restclient.autoconfigure.RestClientBuilderConfigurer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class HttpInterfaceConfig {

    @Bean
    @LoadBalanced
    @Primary
    public WebClient.Builder webClientBalanced() {
        return WebClient.builder();
    }

    @Bean
    public HttpInterfaceProvider webClientHttpInterface(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder
                .baseUrl("http://provider")
                .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();
        HttpInterfaceProvider service = factory.createClient(HttpInterfaceProvider.class);
        return service;
    }

//    @Bean
//    @LoadBalanced
//    public RestClient.Builder restClientBalanced() {
//        return RestClient.builder();
//    }
//
//    @Bean
//    public HttpInterfaceProvider restClientHttpInterface(RestClient.Builder restClientBuilder) {
//        RestClient restClient = restClientBuilder
//                .baseUrl("http://provider")
//                .build();
//        RestClientAdapter adapter = RestClientAdapter.create(restClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builderFor(adapter)
//                .build();
//        HttpInterfaceProvider service = factory.createClient(HttpInterfaceProvider.class);
//        return service;
//    }

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplateBalanced() {
//        return new RestTemplate();
//    }
//
//    @Bean
//    public HttpInterfaceProvider restTemplateHttpInterface(RestTemplate restTemplate) {
//        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://provider"));
//        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builderFor(adapter)
//                .build();
//        HttpInterfaceProvider service = factory.createClient(HttpInterfaceProvider.class);
//        return service;
//    }
}
