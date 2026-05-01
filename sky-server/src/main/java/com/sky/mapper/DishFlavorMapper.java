package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void addFlavor(List<DishFlavor> flavors);
@Select("select * from sky_take_out.dish_flavor where dish_id=#{dishId}")
    List<DishFlavor> queryById(Long dishId);

@Delete("delete  from sky_take_out.dish_flavor where dish_id=#{dishId}")
    void deleteById(Long dishId);
}
