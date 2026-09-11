package com.info.configdemo.Controller;

import com.info.configdemo.Configuration.ConfigServer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ConfigServerController {
    private final ConfigServer configServer;

    @GetMapping("/config-server")
    public String getBuildId() {
        return "Build ID: " + configServer.getId() + ", Version: " + configServer.getVersion() + ", Name: " + configServer.getName();
    }
}
