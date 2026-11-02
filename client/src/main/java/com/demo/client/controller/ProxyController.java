package com.demo.client.controller;

import com.demo.client.service.Service2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProxyController {

    private final Service2Client service2Client;

    @GetMapping("/proxy")
    public String proxy() {
        return service2Client.fetchData();
    }
}
