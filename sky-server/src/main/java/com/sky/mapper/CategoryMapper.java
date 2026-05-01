package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {
    @Insert("insert into sky_take_out.category( type, name, sort, status, create_time, update_time, create_user, update_user)" +
            "values (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser}) ")
    @AutoFill(value = OperationType.INSERT)
    void saveType(Category category);

    Page<Category> queryPage(CategoryPageQueryDTO categoryPageQueryDTO);
@AutoFill(value = OperationType.UPDATE)
    void update(Category categroy);
@Select("select * from sky_take_out.category where type=#{type}")
    Category[] queryType(Integer type);
@Delete("delete from sky_take_out.category where id=#{id}")
    void delete(Long id);
}
