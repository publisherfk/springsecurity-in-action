package com.heshi.customlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author by fukun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CustomLoginApp {
    public static void main(String args[]) {
        SpringApplication.run(CustomLoginApp.class, args);
    }
}
