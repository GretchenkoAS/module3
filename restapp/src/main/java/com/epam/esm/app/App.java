package com.epam.esm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
@EntityScan(basePackages = "com.epam.esm.entity")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
