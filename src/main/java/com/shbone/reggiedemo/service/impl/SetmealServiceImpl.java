package com.shbone.reggiedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shbone.reggiedemo.dto.SetmealDTO;
import com.shbone.reggiedemo.entity.Dish;
import com.shbone.reggiedemo.entity.Setmeal;
import com.shbone.reggiedemo.entity.SetmealDish;
import com.shbone.reggiedemo.mapper.SetmealMapper;
import com.shbone.reggiedemo.service.SetmealDishService;
import com.shbone.reggiedemo.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetMealDishServiceImpl setMealDishService;
    /**
     * 新增菜品套餐，同时添加菜品和套餐的关系
     * @param setmealDTO
     */
    @Transactional
    public void saveSetmealDTO(SetmealDTO setmealDTO) {

        //    插入setmeal套餐信息,操作setmeal表
        Setmeal setmeal = (Setmeal) setmealDTO;
        this.save(setmeal);
        log.info("成功保存套餐信息：{}",setmeal.toString());
        //    添加菜品和套餐的关系，操作setmeal_dish表
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDTO.getId());
            return item;
        }).collect(Collectors.toList());
        setMealDishService.saveBatch(setmealDishes);

    }
}
