package com.heshi.config.controller;

import com.heshi.config.config.GreenDillBaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/config")
public class TestController {

    @Autowired
    private GreenDillBaseConfig baseConfig;

    @GetMapping("/")
    public String test1() {
        return baseConfig.getName();
    }
}
