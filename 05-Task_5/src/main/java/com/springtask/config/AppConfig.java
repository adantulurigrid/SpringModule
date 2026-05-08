package com.springtask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public String projectName() {
        return "Spring Profiles Demo Project";
    }
}