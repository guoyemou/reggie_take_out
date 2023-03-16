package com.yezi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yezi.entity.Employee;
import com.yezi.mapper.EmployeeMapper;
import com.yezi.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
