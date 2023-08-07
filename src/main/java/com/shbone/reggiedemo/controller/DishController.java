package com.shbone.reggiedemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shbone.reggiedemo.common.R;
import com.shbone.reggiedemo.dto.DishDTO;
import com.shbone.reggiedemo.entity.Category;
import com.shbone.reggiedemo.entity.Dish;
import com.shbone.reggiedemo.service.impl.CategoryServiceImpl;
import com.shbone.reggiedemo.service.impl.DishServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunhb
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Resource(type = DishServiceImpl.class)
    private DishServiceImpl dishService;

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * 新增菜品
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO.toString());
        dishService.saveWithFlavor(dishDTO);
        return R.success("成功插入新菜品");
    }
    @PutMapping
    public R<String> update(@RequestBody DishDTO dishDTO){
        log.info("更新菜品：{}",dishDTO.toString());
        dishService.updateWithFlavor(dishDTO);
        return R.success("成功更新菜品");
    }
    /**
     * 查询菜品
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> search(int page, int pageSize){

        Page<Dish> dishPage = new Page<>(page,pageSize);
        Page<DishDTO> dishDTOPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        dishService.page(dishPage,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(dishPage,dishDTOPage,"records");

        List<Dish> dishPageRecords = dishPage.getRecords();

        List<DishDTO> dishDTOList=dishPageRecords.stream().map((item)->{
            DishDTO dishDTO = new DishDTO();
            BeanUtils.copyProperties(item,dishDTO);
            Long categoryId = item.getCategoryId();
            if(categoryId != null){
                Category category = categoryService.getById(categoryId);
                String name = category.getName();
                dishDTO.setCategoryName(name);
            }

            return dishDTO;
        }).collect(Collectors.toList());
        dishDTOPage.setRecords(dishDTOList);

        return  R.success(dishDTOPage);
    }

    /**
     * 根据菜品ID查询相应菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDTO> editDish(@PathVariable Long id){
        log.info("菜品id：{}",id);
        DishDTO dishDTO = dishService.getByIdWithFlavors(id);
        return R.success(dishDTO) ;
    }


    @GetMapping("/list")
    public R<List<Dish>> listCategory(Dish dish){
        log.info("套餐信息:{}",dish.toString());

        Long categoryId = dish.getCategoryId();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(categoryId != null,Dish::getCategoryId,categoryId);
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> dishList = dishService.list(queryWrapper);
        return R.success(dishList);
    }
}
