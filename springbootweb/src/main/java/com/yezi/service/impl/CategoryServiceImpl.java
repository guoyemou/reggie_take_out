package com.yezi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yezi.common.CustomException;
import com.yezi.entity.Category;
import com.yezi.mapper.CategoryMapper;
import com.yezi.service.CategoryService;
import com.yezi.service.DishService;
import com.yezi.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {
    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;
    //    删除条件

    @Override
    public boolean remove(long ids){
//        查询当前分类是否关联了菜品，
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("category_id",ids);
        int count = dishService.count(wrapper);
        if(count > 0){
            throw new CustomException("当前分类关联了菜品，不能删除");
        }
//        查询当前套餐是否关联了菜品，
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("category_id",ids);
        int count1 = setmealService.count(wrapper);
        if(count1 > 0){
            throw new CustomException("当前套餐关联了菜品，不能删除");
        }
       return super.removeById(ids);
    }
}
