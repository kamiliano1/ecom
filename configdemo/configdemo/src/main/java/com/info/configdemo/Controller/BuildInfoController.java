package com.info.configdemo.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildInfoController {

    @Value("${build.id:defaultId}")
    private String buildId;

    @Value("${build.version:defaultVersion}")
    private String buildVersion;

    @Value("${build.name:defaultName}")
    private String buildName;

    //  Env OS
    @Value("${OS:defaultId}")
    private String getOS;
    @Value("${USERPROFILE:defaultId}")
    private String getUserProfile;

    // Placeholder
    @Value("${build.placeholder:defaultHolder}")
    private String getplaceholder;

    @GetMapping("/build-info")
    public String getBuildId() {
        return "Build ID: " + buildId + ", Version: " + buildVersion + ", Name: " + buildName
                + ",\n Env from Operation System OS: " + getOS + ", UserProfile: " + getUserProfile
                + ",\n Placeholder : " + getplaceholder;
    }
}
