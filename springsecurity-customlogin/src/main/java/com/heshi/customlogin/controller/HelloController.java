package com.heshi.customlogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by fukun
 */
@Controller
@RequestMapping("/base")
public class HelloController {
    @GetMapping("/")
    public String helo() {
        return "base/hello";
    }
}
