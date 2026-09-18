package com.demo.provider.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceController {
    @Value("${server.port:default}")
    private String port;

    private final String instanceId = java.util.UUID.randomUUID().toString();

    @GetMapping("/instance-info")
    public String getInstanceInfo() {
        System.out.println("Request received at instance on port: " + port);
        return "Instance served by port: " + port + " Instance ID: " + instanceId;
    }
}
