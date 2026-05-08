package com.springtask.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    public DatabaseConfig() {
        System.out.println("DatabaseConfig loaded");
    }
}