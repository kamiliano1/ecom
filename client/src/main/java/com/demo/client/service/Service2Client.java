package com.demo.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class Service2Client {

    private final RestTemplate restTemplate;
    private final OAuth2AuthorizedClientManager manager;
    @Value("${service2.url}")
    String service2Url;

    public String fetchData() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String incomingToken = null;
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            incomingToken = jwtAuthenticationToken.getToken()
                    .getTokenValue();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(incomingToken);

        var resp = restTemplate.exchange(
                service2Url + "/data",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                String.class
        );
        return resp.getBody();
    }
//    Generate a new token every time
//    public String fetchData() {
//        var authRequest = OAuth2AuthorizeRequest
//                .withClientRegistrationId("keycloak-client")
//                .principal("machine")
//                .build();
//        var client = manager.authorize(authRequest);
//        String token = client.getAccessToken().getTokenValue();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setBearerAuth(token);
//
//        var resp = restTemplate.exchange(
//                service2Url + "/data",
//                HttpMethod.GET,
//                new HttpEntity<>(httpHeaders),
//                String.class
//        );
//        return resp.getBody();
//    }

    ;


}
