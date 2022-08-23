package com.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudAlibabaSentinelService8004Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaSentinelService8004Application.class, args);
        System.out.println("-----success--------");
    }

}
