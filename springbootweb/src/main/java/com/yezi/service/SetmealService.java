package com.yezi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yezi.dto.SetmealDto;
import com.yezi.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {
    void saveSetmeal(SetmealDto setmealDto);
}
