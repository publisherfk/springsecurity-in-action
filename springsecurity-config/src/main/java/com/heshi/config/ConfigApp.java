package com.heshi.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author by fukun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ConfigApp {
    public static void main(String args[]) {
        SpringApplication.run(ConfigApp.class, args);
    }
}
