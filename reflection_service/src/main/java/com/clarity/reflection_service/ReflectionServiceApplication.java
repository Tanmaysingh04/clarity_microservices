package com.clarity.reflection_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReflectionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReflectionServiceApplication.class, args);
    }
}