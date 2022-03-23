package com.toowis.simple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {
    
    @GetMapping("/get")
    public String getRest() {
        return "getRest";
    }
}
