package com.supcon.greendill.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/oauthClient")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "客户端";
    }
}
