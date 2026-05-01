package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @Select("select count(*) from sky_take_out.setmeal where category_id=#{id}")
    Integer countByCategoryId(Long id);
@AutoFill(value = OperationType.INSERT)
@Options(useGeneratedKeys = true,keyProperty = "id")
    void addSetmeal(Setmeal setmeal);

    Page<SetmealVO> queryByPage(SetmealPageQueryDTO setmealPageQueryDTO);
@AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);
@Select("select s.*,c.name as categoryName  from sky_take_out.setmeal s left join sky_take_out.category c on s.category_id = c.id where s.id=#{id}")
    SetmealVO queryById(Long id);

    void deleteSetmeal(List<Long> ids);
}
