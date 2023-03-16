package com.yezi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yezi.entity.OrderDetail;
import com.yezi.mapper.OrderDetailMapper;
import com.yezi.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}