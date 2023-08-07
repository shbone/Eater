package com.shbone.reggiedemo.dto;

import com.shbone.reggiedemo.entity.Dish;
import com.shbone.reggiedemo.entity.Setmeal;
import com.shbone.reggiedemo.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @author: SunHB
 * @createTime: 2023/08/06 下午1:21
 * @description:添加套餐
 */
@Data
public class SetmealDTO extends Setmeal {
    private String categoryName;
    private List<SetmealDish> setmealDishes;
    private List<Dish> dishList;
}
