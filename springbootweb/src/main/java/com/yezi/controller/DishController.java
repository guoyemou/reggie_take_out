package com.yezi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yezi.common.R;
import com.yezi.config.WebMvcConfig;
import com.yezi.dto.DishDto;
import com.yezi.entity.Category;
import com.yezi.entity.Dish;
import com.yezi.entity.DishFlavor;
import com.yezi.service.CategoryService;
import com.yezi.service.DishFlavorService;
import com.yezi.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 保存菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> saveDish(@RequestBody DishDto dishDto){
        dishService.saveDish(dishDto);
        String key = "dish_"+dishDto.getCategoryId()+"_1";
        Set keys = redisTemplate.keys(key);
        redisTemplate.delete(keys);
        return R.success("成功");
    }

    /**
     * 菜品管理的分页
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> dishPage(int page,int pageSize,String name){
        Page pageInfo = new Page(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page,pageSize);
        QueryWrapper wrapper = new QueryWrapper();
        if(name != null){
            wrapper.like("name",name);
        }
        wrapper.orderByDesc("update_time");
        dishService.page(pageInfo,wrapper);
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item ->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            if(categoryName != null){
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        })).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return  R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品并回显数据
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> selectByIdDish(@PathVariable Long id){
       return R.success(dishService.selectByIdDish(id));
    }
    /**
     *修改菜品
     */
    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto){
        dishService.updateDish(dishDto);
        String key = "dish_"+dishDto.getCategoryId()+"_1";
        Set keys = redisTemplate.keys(key);
        redisTemplate.delete(keys);
        return R.success("修改成功");
    }

    /**
     * 删除一个菜品or多个菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @Transactional
    public R<String> deleteByIdDish(Long[] ids){
        List<Long> idList = Arrays.asList(ids);
        dishService.removeByIds(idList);
        dishFlavorService.removeByIds(idList);
        return R.success("删除成功");
    }

    /**
     * 修改一个菜品状态or多个菜品状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateByIdStatus(@PathVariable int status,Long[] ids){
        for (Long id : ids) {
            Dish dish = dishService.getById(id);
            QueryWrapper<Dish> wrapper = new QueryWrapper<>();
            wrapper.eq("id",id);
                dish.setStatus(status);
                dishService.update(dish,wrapper);
        }
        return R.success("修改成功");
    }

    /**
     * 查询菜品，用来新建套餐回显数据
     * @param
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> selectByIdSetmeal(Dish dish){
        List<DishDto> dishDtoList = null;
        String key = "dish_"+dish.getCategoryId()+"_"+dish.getStatus();
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if(dishDtoList != null){
            return R.success(dishDtoList);
        }
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        dishDtoList = list.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> list1 = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());
        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);
        return  R.success(dishDtoList);
    }
}
