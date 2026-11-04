package com.demo.pkce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**")
                .authenticated()
        ).oauth2ResourceServer(ouath2 -> ouath2.jwt(
                Customizer.withDefaults()
        ));
        return httpSecurity.build();
    }
}
