package com.demo.auth_code_flow.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(OAuth2AuthenticationToken token) {
        return "Welcome Home, Hello "
                + token.getPrincipal().getAttribute("name")
                + "\n, " + token.getPrincipal().getAttribute("email")
                + "\n, " + token.getAuthorities();
    }
}
