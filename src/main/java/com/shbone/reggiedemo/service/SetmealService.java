package com.shbone.reggiedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shbone.reggiedemo.dto.SetmealDTO;
import com.shbone.reggiedemo.entity.Setmeal;


/**
 * @author sunhb
 */

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增菜品套餐，同时添加菜品和套餐的关系
     * @param setmealDTO
     */
    public void saveSetmealDTO(SetmealDTO setmealDTO);

}
