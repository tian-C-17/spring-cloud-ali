package com.cloud.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class DeptController_Consumer {

    @Resource
    private RestTemplate restTemplate;

    // //服务提供者的服务名
    @Value("${service-url.nacos-user-service}")
    private String serverUrl;

    @GetMapping(value = "/consumer/dept/nacos/{id}")
    public String paymantInfo(@PathVariable("id") Long id){
        System.out.println("-----------8002--------");
        return restTemplate.getForObject(serverUrl+"/dept/nacos/"+id,String.class);

    }

}
