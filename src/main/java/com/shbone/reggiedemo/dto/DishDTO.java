package com.shbone.reggiedemo.dto;

import com.shbone.reggiedemo.entity.Dish;
import com.shbone.reggiedemo.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDTO extends Dish {
    private List<DishFlavor> flavors =new ArrayList<>();
    private String categoryName;
    private Integer copies;

}
