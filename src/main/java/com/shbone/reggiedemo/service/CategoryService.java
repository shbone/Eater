package com.shbone.reggiedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shbone.reggiedemo.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
