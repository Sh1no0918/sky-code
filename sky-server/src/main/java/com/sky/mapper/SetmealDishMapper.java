package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
@Select("select  setmeal_id from sky_take_out.setmeal_dish where dish_id=#{dishId}")
    List<Long> getSetmealIdQueryByDishId(Long dishId);

    void addSetmealDish(SetmealDish setmealDish);
@Select("select * from sky_take_out.setmeal_dish where setmeal_id =#{setmealId}")
    List<SetmealDish> QueryBySetmealId(Long setmealId);
@Delete("delete from sky_take_out.setmeal_dish where setmeal_id=#{id}")
    void deleteBySetmealId(Long id);
}
