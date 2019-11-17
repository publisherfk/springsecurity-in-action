package com.heshi.acl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.heshi.acl", "com.heshi.audit"})
public class AclApp {
    public static void main(String args[]) {
        SpringApplication.run(AclApp.class, args);
    }
}
