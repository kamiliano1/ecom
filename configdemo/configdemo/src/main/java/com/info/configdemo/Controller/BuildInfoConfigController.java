package com.info.configdemo.Controller;

import com.info.configdemo.Configuration.BuildInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BuildInfoConfigController {

    private final BuildInfo buildInfo;

    @GetMapping("/build-info-config")
    public String getBuildId() {
        return "Build ID: " + buildInfo.getId() + ", Version: " + buildInfo.getVersion() + ", Name: " + buildInfo.getName();

    }
}
