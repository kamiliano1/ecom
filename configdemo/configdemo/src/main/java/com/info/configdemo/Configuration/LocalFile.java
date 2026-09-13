package com.info.configdemo.Configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "localfile")
@Data
public class LocalFile {
    private String id;
    private String version;
    private String name;
    private String type;
}
