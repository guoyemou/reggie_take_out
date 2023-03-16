package com.yezi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yezi.dto.DishDto;
import com.yezi.dto.SetmealDto;
import com.yezi.entity.Setmeal;
import com.yezi.entity.SetmealDish;
import com.yezi.mapper.SetmealDishMapper;
import com.yezi.service.SetmealDishService;
import com.yezi.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
