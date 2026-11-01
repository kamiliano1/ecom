package com.demo.resource.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        httpSecurity.authorizeHttpRequests(a -> a
                .anyRequest().authenticated()
        ).oauth2ResourceServer(oauth2 ->
                oauth2.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }
}
