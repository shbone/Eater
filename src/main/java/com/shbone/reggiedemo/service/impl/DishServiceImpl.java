package com.shbone.reggiedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shbone.reggiedemo.dto.DishDTO;
import com.shbone.reggiedemo.entity.Dish;
import com.shbone.reggiedemo.entity.DishFlavor;
import com.shbone.reggiedemo.mapper.DishMapper;
import com.shbone.reggiedemo.service.DishFlavorService;
import com.shbone.reggiedemo.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Resource(type = DishFlavorServiceImpl.class)
    private DishFlavorServiceImpl dishFlavorService;
    // @Resource(type = DishServiceImpl.class)
    // private DishServiceImpl dishService;


    @Transactional(rollbackFor = Exception.class)
    public  void saveWithFlavor(DishDTO dishDTO){
        this.save(dishDTO);
        //菜品ID
        Long id = dishDTO.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.stream().map((item)->{
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }


    @Override
    public DishDTO getByIdWithFlavors(Long id) {
        //查询口味信息和菜品信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id);
        List<DishFlavor> dishFlavor = dishFlavorService.list(queryWrapper);

        Dish dish = this.getById(id);
        DishDTO dishDTO = new DishDTO();
        BeanUtils.copyProperties(dish,dishDTO);
        dishDTO.setFlavors(dishFlavor);
        //组合成DishDTO
        return dishDTO;
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIdWithFlavor(Long id) throws Exception {
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getId, id);
        boolean remove = dishFlavorService.remove(queryWrapper);
        boolean b = updateWithFlavor(id);
        return remove;
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWithFlavor(Long id) throws Exception {
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getId, id);
        boolean remove = dishFlavorService.saveOrUpdateBatch(dishFlavorService.list(queryWrapper));
        //主动抛出Exception
        if(remove == false || remove == true){
            throw new Exception("主动抛出异常");
        }
        return remove;
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateWithFlavor(DishDTO dishDTO) {
        this.updateById(dishDTO);
        //菜品ID
        Long id = dishDTO.getId();
        //根据菜品ID，删除口味
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
        //重新添加相应的口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.stream().map((item)->{
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}
