package com.shbone.reggiedemo.controller;

import com.shbone.reggiedemo.common.R;
import com.shbone.reggiedemo.dto.SetmealDTO;
import com.shbone.reggiedemo.entity.Setmeal;
import com.shbone.reggiedemo.service.impl.SetmealServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: SunHB
 * @createTime: 2023/08/06 下午10:01
 * @description: 套餐
 */
@RestController
@Slf4j
public class SetmearlController {
    @Autowired
    private SetmealServiceImpl setmealService;


    @PostMapping("/setmeal")
    public R<String> save(@RequestBody SetmealDTO setmealDTO){
        log.info("保存的套餐信息：{}",setmealDTO.toString());
        setmealService.saveSetmealDTO(setmealDTO);
        return R.success("成功添加套餐"+setmealDTO.toString());
    }
}
