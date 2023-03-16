package com.yezi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yezi.entity.DishFlavor;
import com.yezi.mapper.DishFlavorMapper;
import com.yezi.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper,DishFlavor> implements DishFlavorService{
}
