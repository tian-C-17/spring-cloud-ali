package com.cloud.demo.service;

import com.cloud.demo.entry.Dept;

import java.util.List;

public interface DeptService {
    Dept get(Integer deptNo);
    List<Dept> selectAll();
}
