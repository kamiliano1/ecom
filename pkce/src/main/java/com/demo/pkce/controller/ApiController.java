package com.demo.pkce.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/api/home")
    public String home(@AuthenticationPrincipal Jwt jwt) {
        String surname = jwt.getClaim("preferred_username");
        String firstname = jwt.getClaim("given_name");
        String lastname = jwt.getClaim("family_name");
        
        return "Hello home: " + surname + " " + firstname + " " + lastname;
    }
}
