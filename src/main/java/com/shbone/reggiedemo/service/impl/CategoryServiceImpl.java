package com.shbone.reggiedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shbone.reggiedemo.common.CustomException;
import com.shbone.reggiedemo.entity.Category;
import com.shbone.reggiedemo.entity.Dish;
import com.shbone.reggiedemo.entity.Setmeal;
import com.shbone.reggiedemo.mapper.CategoryMapper;
import com.shbone.reggiedemo.service.CategoryService;
import com.shbone.reggiedemo.service.EmployeeService;
import com.shbone.reggiedemo.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl  extends ServiceImpl<CategoryMapper,Category> implements CategoryService {
    @Autowired
    private DishServiceImpl dishService;
    @Autowired
    private SetmealService setmealService;


    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count > 0){
        //    有菜品关联，删除失败
            throw new CustomException("有菜品关联，删除失败");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if(count1> 0){
        //    有套餐关联，删除失败
            throw new CustomException("有套餐管理，无法删除");
        }

        super.removeById(id);

    }
}
