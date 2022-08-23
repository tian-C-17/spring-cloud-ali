package com.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringCloudAlibabaConsumer8006Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaConsumer8006Application.class, args);
        System.out.println("-------success-------");
    }

}
