package com.security.Jwt.Implementation.Project.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/protected")
    public String protectedApi() {
        return "You have accessed a protected API successfully!";
    }
}
