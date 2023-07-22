package com.shbone.reggiedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shbone.reggiedemo.entity.Dish;
import com.shbone.reggiedemo.entity.Setmeal;
import com.shbone.reggiedemo.mapper.SetmealMapper;
import com.shbone.reggiedemo.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

}
