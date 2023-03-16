package com.yezi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yezi.common.R;
import com.yezi.entity.Category;
import com.yezi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 新建菜品类型
     * @return
     */
    @PostMapping
    public R<String> saveDishesType(@RequestBody Category category){
        boolean save = categoryService.save(category);
        if(save){
           return   R.success("新建菜品类型成功");
        }else {
           return R.error("新建菜品类型失败");
        }
    }
    /**
     * 修改菜品类型
     * @return
     */
    @PutMapping
    public R<Category> updateDishesType(@RequestBody Category category){
        boolean b = categoryService.updateById(category);
        if(b){
            return R.success(category);
        }else {
            return R.error("修改失败");
        }
    }
    /**
     * 删除菜品类型
     * @return
     */
    @DeleteMapping
    public R<String> deleteDishesType(Long ids){
        boolean remove = categoryService.remove(ids);
        if(remove){
            return R.success("删除成功");
        }else {
            return R.success("删除失败");
        }

    }
    /**
     * 分类管理的分页
     * @return
     */
    @GetMapping("/page")
    public R<Page> classifyToManagePage(int page, int pageSize){
        Page pageInfo  = new Page(page,pageSize);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("sort");
        categoryService.page(pageInfo,wrapper);
        return R.success(pageInfo);
    }
    /**
     * 菜品分类的信息
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> MenuClassification(Category category){
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        if(category.getType() != null) {
            wrapper.eq("type", category.getType());
        };
        List<Category> list = categoryService.list(wrapper);
        return  R.success(list);
    }
}
