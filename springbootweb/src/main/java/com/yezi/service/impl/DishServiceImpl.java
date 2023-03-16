package com.yezi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yezi.dto.DishDto;
import com.yezi.entity.Dish;
import com.yezi.entity.DishFlavor;
import com.yezi.mapper.DishMapper;
import com.yezi.service.DishFlavorService;
import com.yezi.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    /**
     * 保存菜品
     */
    @Autowired
    DishFlavorService dishFlavorService;
    @Override
    @Transactional
    public void saveDish(DishDto dishDto) {
        this.save(dishDto);
        //        获取菜品的id
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) ->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品并回显数据
     * @param id
     * @return
     */
    @Override
    public DishDto selectByIdDish(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
//        查询当前菜品对应的口味信息，从dish flavor表查询
        QueryWrapper<DishFlavor> wrapper = new QueryWrapper();
        wrapper.eq("dish_id",dish.getId());
        List<DishFlavor> list = dishFlavorService.list(wrapper);
        dishDto.setFlavors(list);
        return  dishDto;
    }

    /**
     * 修改菜品
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateDish(DishDto dishDto) {
//        更新dish表基信息
        this.updateById(dishDto);
//        清理当前菜品对应口味数据---dish flavor表的delete操作
        QueryWrapper<DishFlavor> wrapper = new QueryWrapper<>();
        wrapper.eq("dish_id",dishDto.getId());
        dishFlavorService.remove(wrapper);
//        添加提交的口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) ->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}
