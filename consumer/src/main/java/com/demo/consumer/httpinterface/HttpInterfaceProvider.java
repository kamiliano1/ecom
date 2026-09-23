package com.demo.consumer.httpinterface;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface HttpInterfaceProvider {
    
    @GetExchange("/instance-info")
    String getInstanceInfo();
}
