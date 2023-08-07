package com.shbone.reggiedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shbone.reggiedemo.dto.DishDTO;
import com.shbone.reggiedemo.entity.Dish;

/**
 * @author sunhb
 */
public interface DishService extends IService<Dish> {

    DishDTO getByIdWithFlavors(Long id);


}
