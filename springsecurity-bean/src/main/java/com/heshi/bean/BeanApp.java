package com.heshi.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author by fukun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BeanApp {
    public static void main(String args[]) {
        SpringApplication.run(BeanApp.class, args);
    }
}
