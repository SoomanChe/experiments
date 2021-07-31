package com.sooman.slack_spring_integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SlackSpringIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackSpringIntegrationApplication.class, args);
    }

}
