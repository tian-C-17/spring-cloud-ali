package com.cloud.demo.service;

import com.cloud.demo.entry.CommonResult;
import com.cloud.demo.entry.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(value = "spring-cloud-provider-8005",fallback = DeptFeignService.class)
public interface DeptFeignService {

    @GetMapping(value = "/dept/get/{id}")
    public CommonResult<Dept> get(@PathVariable("id") int id);

    @GetMapping(value = "/dept/list")
    public CommonResult<List<Dept>> list();
}
