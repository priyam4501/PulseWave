package com.pulsewave.chatservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/chat/test")
    public String test() {

        return "Protected chat endpoint accessed";
    }
}