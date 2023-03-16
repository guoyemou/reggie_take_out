package com.yezi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yezi.common.R;
import com.yezi.entity.Category;
import com.yezi.entity.Employee;
import com.yezi.service.EmployeeService;
import com.yezi.utils.DateUtils;
import com.yezi.utils.Md5Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 登陆界面
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
        System.out.println("111");
//        1-对提交的的秘密进行的MD5加密
        String md5PassWord = Md5Utils.getMD5(employee.getPassword());
//        2-将页面提交的用户名username查询数据库
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("username",employee.getUsername());
        Employee one = employeeService.getOne(wrapper);
        if(one == null){
          return   R.error("没有查询到该用户");
        }
        System.out.println(md5PassWord);
        if(!one.getPassword().equals(md5PassWord)){
         return    R.error("用户的密码错误");
        }
        if(one.getStatus() == 0){
         return    R.error("用户已被锁定");
        }
        request.getSession().setAttribute("id",one.getId());
        return R.success(one);
    }

    /**
     * 用户退出
     * @param
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> login( HttpServletRequest request){
        request.getSession().removeAttribute("id");
        return R.success("退出成功");
    }

    /**
     * 添加员工
     * @return
     */
    @PostMapping
    public R<String> saveEmployee(@RequestBody Employee employee,HttpServletRequest request){
        employee.setPassword(Md5Utils.getMD5("000000"));
//        employee.setCreateTime(String.valueOf(DateUtils.parse(new Date())));
//        employee.setUpdateTime(String.valueOf(DateUtils.parse(new Date())));
//        employee.setCreateUser((Long) request.getSession().getAttribute("id"));
        boolean save = employeeService.save(employee);
        if(save){
            return R.success("添加员工成功");
        }
        return R.error("添加员工失败");
    };

    /**
     * 执行分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
//        构造分页构造器
        Page pageInfo = new Page(page,pageSize);
//        构造条件构造器
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        if(name != null){
            wrapper.like("name",name);
        }
        wrapper.orderByDesc("create_time");
        employeeService.page(pageInfo,wrapper);
        return  R.success(pageInfo);
    }

    /**
     * 修改员工的状态 0-禁用 1-启动
     * @param employee
     * @param request
     */
    @PutMapping
    public R<String> updateStatus(@RequestBody Employee employee,HttpServletRequest request){
        employee.setUpdateUser((Long) request.getSession().getAttribute("id"));
        employee.setUpdateTime(DateUtils.parse(new Date()));
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        try {
            employeeService.updateById(employee);
            return R.success("修改状态成功");
        }catch (Exception e){
            return R.success("修改状态失败");
        }
    }

    /**
     * 编辑员工
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> updateEmployee(@PathVariable Long id){
        log.info("进入编辑员工");
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    };

}
