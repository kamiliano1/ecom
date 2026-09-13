package com.info.configdemo.Controller;

import com.info.configdemo.Configuration.LocalFile;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LocalFileController {

    private final LocalFile localFile;

    @GetMapping("/local-file")
    private String getLocalData() {
        return "Build ID: " + localFile.getId() + ", Version: " + localFile.getVersion() + ", Name: " + localFile.getName() + ", Type: " + localFile.getType();

    }
}
