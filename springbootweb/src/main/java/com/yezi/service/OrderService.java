package com.yezi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yezi.entity.Orders;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    void submit(Orders orders);
}
