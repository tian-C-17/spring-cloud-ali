package com.cloud.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 开启 Nacos 服务发现功能
public class SpringCloudAlibabaProvide8001Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaProvide8001Application.class, args);
        System.out.println("--------success---------");
    }

}
