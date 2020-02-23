package com.supcon.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ReactiveApp {
    public static void main(String args[]) {
        SpringApplication.run(ReactiveApp.class, args);
    }
}
