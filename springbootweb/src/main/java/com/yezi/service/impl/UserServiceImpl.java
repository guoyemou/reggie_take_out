package com.yezi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yezi.entity.User;
import com.yezi.mapper.UserMapper;
import com.yezi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
