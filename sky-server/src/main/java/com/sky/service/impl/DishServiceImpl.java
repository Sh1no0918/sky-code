package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    @Transactional
//    添加菜品
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dish.setStatus(StatusConstant.DISABLE);
        dishMapper.addDish(dish);
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
//        判断菜品有无口味,有口味则添加菜品口味
        if(flavors!=null && flavors.size()>0){
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            dishFlavorMapper.addFlavor(flavors);
        }


    }
//分页查询菜品
    @Override
    public PageResult queryPageDish(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
       Page<DishVO> dishes= dishMapper.queryPageDish(dishPageQueryDTO);
        long total = dishes.getTotal();
        return new PageResult(total,dishes);
    }

    @Override
//    根据id查询菜品
    public DishVO queryById(Long id) {
       DishVO dish= dishMapper.queryById(id);
       List<DishFlavor> dishFlavors=dishFlavorMapper.queryById(id);
       if(dishFlavors !=null && dishFlavors.size()>0){
           dish.setFlavors(dishFlavors);
       }
        return dish;
    }

    @Override
    public Dish[] queryByCatrgoryController(Long categoryId) {
        Dish[] dishes=dishMapper.queryCategoryController(categoryId);
        return dishes;
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder().status(status)
                .id(id)
                .build();
        dishMapper.update(dish);
    }

    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        dishFlavorMapper.deleteById(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors !=null && flavors.size()>0){
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishDTO.getId());
            }
            dishFlavorMapper.addFlavor(flavors);
        }

    }

    @Override
    public void delete(List<Long> ids) {
//        判断菜品是否是起售中
        for (Long id : ids) {
            DishVO dish = dishMapper.queryById(id);
            if(dish.getStatus()==StatusConstant.ENABLE){
                //当前菜品为起售中
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
//判断菜品是否相关联套餐
        for (Long dishId : ids) {
           List<Long> setmealIds=setmealDishMapper.getSetmealIdQueryByDishId(dishId);

        }
//        删除菜品数据
        dishMapper.delete(ids);
//        删除菜品关联的口味数据
        for (Long id : ids) {
            dishFlavorMapper.deleteById(id);
        }
    }
}
