package com.cloud.demo.controller;

import com.cloud.demo.entry.CommonResult;
import com.cloud.demo.entry.Dept;
import com.cloud.demo.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/dept/get/{id}")
    public CommonResult<Dept> get(@PathVariable("id") int id) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Dept dept = deptService.get(id);
        CommonResult<Dept> result = new CommonResult(200, "from mysql,serverPort:  " + serverPort, dept);
        return result;
    }

    @GetMapping(value = "/dept/list")
    public CommonResult<List<Dept>> list() {
        List<Dept> depts = deptService.selectAll();
        CommonResult<List<Dept>> result = new CommonResult(200, "from mysql,serverPort:  " + serverPort, depts);
        return result;
    }
}
