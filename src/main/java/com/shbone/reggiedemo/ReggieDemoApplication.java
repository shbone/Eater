package com.shbone.reggiedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ReggieDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieDemoApplication.class, args);
    }

}
