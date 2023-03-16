package com.yezi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yezi.common.R;
import com.yezi.dto.DishDto;
import com.yezi.dto.SetmealDto;
import com.yezi.entity.Category;
import com.yezi.entity.Setmeal;
import com.yezi.service.CategoryService;
import com.yezi.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    SetmealService setmealService;
    @Autowired
    CategoryService categoryService;
    /**
     * 新建套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> saveSetmeal(@RequestBody SetmealDto setmealDto){
        setmealService.saveSetmeal(setmealDto);
        return R.success("添加成功");
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteByIdsSetmeal(Long[] ids){
        List<Long> asList = Arrays.asList(ids);
        setmealService.removeByIds(asList);
        return R.success("删除成功");
    }

    /***
     * 通过id修改套餐状态
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateByIdsStatus(@PathVariable int status,Long[] ids){
        for (Long id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(status);
            LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper();
            wrapper.eq(Setmeal::getId,id);
            setmealService.update(setmeal,wrapper);
        }
        return R.success("修改成功");
    }
    /**
     * 套餐管理的分页
     */
    @GetMapping("/page")
    public R<Page> SetmealPage(int page,int pageSize,String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> page1 = new Page<>(page,pageSize);
        QueryWrapper wrapper = new QueryWrapper();
        if(name != null){
            wrapper.like("name",name);
        }
        wrapper.orderByDesc(("update_time"));
        setmealService.page(pageInfo,wrapper);
//        对象拷贝
        BeanUtils.copyProperties(pageInfo,page1,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> collect = records.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
//            分类id
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        page1.setRecords(collect);
        return R.success(page1);
    }
}
