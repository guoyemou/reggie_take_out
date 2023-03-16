package com.yezi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yezi.dto.DishDto;
import com.yezi.entity.Dish;
import com.yezi.entity.DishFlavor;

public interface DishService extends IService<Dish> {
    void saveDish(DishDto dishDto);
    DishDto selectByIdDish(Long id);
    void updateDish(DishDto dishDto);
}
