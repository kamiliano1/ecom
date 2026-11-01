package com.demo.auth_code_flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AuthCodeFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthCodeFlowApplication.class, args);
    }


}
