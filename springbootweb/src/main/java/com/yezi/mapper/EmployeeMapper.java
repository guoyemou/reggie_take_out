package com.yezi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yezi.entity.Employee;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
