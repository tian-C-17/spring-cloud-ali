package com.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 开启服务注册与发现功能
public class SpringCloudAlibabaConsumer8002Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaConsumer8002Application.class, args);
        System.out.println("--------success---------");
    }

}
