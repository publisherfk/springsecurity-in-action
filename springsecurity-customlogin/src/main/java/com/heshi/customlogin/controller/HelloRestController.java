package com.heshi.customlogin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/restbase")
public class HelloRestController {
    @GetMapping("/")
    public String hello() {
        return "hello restController";
    }
}
