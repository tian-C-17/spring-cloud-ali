package com.cloud.cn.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {

    @Value("${server.port}")
    private String serverPort;
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping(value = "/dept/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return "服务名称："+appName+"----port:"+serverPort+"---id:"+id;
    }

}
