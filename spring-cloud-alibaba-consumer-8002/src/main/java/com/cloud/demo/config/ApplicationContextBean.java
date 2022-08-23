package com.cloud.demo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 使用 @LoadBalanced 注解与 Ribbon 进行集成开启负载均衡功能
 */
@Configuration
public class ApplicationContextBean {

    @Bean
    @LoadBalanced // 与 Ribbon 集成，并开启负载均衡功能
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
