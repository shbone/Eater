package com.shbone.reggiedemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shbone.reggiedemo.common.R;
import com.shbone.reggiedemo.entity.Category;
import com.shbone.reggiedemo.entity.Setmeal;
import com.shbone.reggiedemo.service.CategoryService;
import com.shbone.reggiedemo.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;


    @PostMapping
    public R<Object> save(@RequestBody Category category){
        log.info("菜单：{}",category.toString());
        boolean save = categoryService.save(category);
        if(save){
            return R.success("保存菜单成功");
        }
        return R.error("保存菜单失败");
    }

    @GetMapping("/page")
    public R<Page> PageR(int page,int pageSize){
        log.info("page:{},pageSize:{}",page,pageSize);
        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Category::getUpdateTime);
        categoryService.page(pageInfo,queryWrapper);
        return  R.success(pageInfo);
    }


    @DeleteMapping
    public R<String> deleteCategory(Long ids){
        log.info("删除ID为{}",ids);
        // categoryService.removeById(id);
        categoryService.remove(ids);
        return R.success("成功删除"+ids);
    }
    @PutMapping
    public R<String> putCategory(@RequestBody Category category){
        log.info("修改菜品分类：{}",category);
        // Long id = category.getId();
        categoryService.updateById(category);
        return R.success("修改分类成功！");
    }

    /**
     * 查询分类列表
     * @param category type 作为category的属性传入
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> getCategoryList( Category category ){
        log.info("分类：{}",category.toString());
        //构造查询器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //查询内容
        queryWrapper.eq(category.getType()!= null,Category::getType,category.getType());
        queryWrapper.orderByDesc(Category::getType).orderByDesc(Category::getUpdateTime);

        List<Category> categories = categoryService.list(queryWrapper);
        return R.success(categories);
    }
}
