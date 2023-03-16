package com.yezi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yezi.entity.Category;

public interface CategoryService extends IService<Category> {
    public boolean remove(long ids);
}
