package com.clarity.habit_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HabitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabitServiceApplication.class, args);
    }
}