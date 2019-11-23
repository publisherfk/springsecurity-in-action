package com.supcon.greendill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author by fukun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Oauth2ClientApp {
    public static void main(String args[]) {
        SpringApplication.run(Oauth2ClientApp.class, args);
    }
}
