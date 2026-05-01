package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {
    @Select("select count(*) from sky_take_out.dish where category_id=#{id} ")
    Integer countByCategoryId(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sky_take_out.dish(name, category_id, price, image, description, " +
            "status,create_time, update_time, create_user, update_user)" +
            "values (#{name},#{categoryId},#{price},#{image},#{description}," +
            "#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void addDish(Dish dish);

    Page<DishVO> queryPageDish(DishPageQueryDTO dishPageQueryDTO);

    @Select("select d.* , c.name as categoryName from sky_take_out.dish d left join sky_take_out.category c on c.id=d.category_id = c.id where d.id=#{id}")
    DishVO queryById(Long id);

    @Select("select * from sky_take_out.dish where category_id=#{ategoryId)}")
    Dish[] queryCategoryController(Long categoryId);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    void delete(List<Long> ids);
}