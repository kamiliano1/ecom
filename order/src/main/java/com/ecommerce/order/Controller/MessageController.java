package com.ecommerce.order.Controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Value("${app.message}")
    private String message;

    @GetMapping("/message")
    @RateLimiter(name = "rateLimiterBreaker", fallbackMethod = "getMessageFallback")
    public String getMessage() {
        return message;
    }

    public String getMessageFallback(Exception e) {
        return "Hello Fallback";
    }
}
