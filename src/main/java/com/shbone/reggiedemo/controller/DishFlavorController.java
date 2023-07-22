package com.shbone.reggiedemo.controller;

import com.shbone.reggiedemo.common.R;
import com.shbone.reggiedemo.dto.DishDTO;
import com.shbone.reggiedemo.entity.DishFlavor;
import com.shbone.reggiedemo.service.DishFlavorService;
import com.shbone.reggiedemo.service.impl.DishFlavorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunhb
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishFlavorController {

    private DishFlavor dishFlavor;

    @Resource(type = DishFlavorServiceImpl.class)
    private DishFlavorServiceImpl dishFlavorService;

    // @PostMapping
    // public R<String> save(@RequestBody DishDTO dishDTO){
    //     log.info("添加菜品：{}",dishDTO.toString());
    //
    //     return R.success("成功添加菜品！");
    // }

}
