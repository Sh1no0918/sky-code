package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl  implements CategoryService {
@Autowired
private CategoryMapper categoryMapper;
@Autowired
private DishMapper dishMapper;
@Autowired
private SetmealMapper setmealMapper;

//新增分类
    @Override
    public void saveType(CategoryDTO categoryDTO) {
        Category category =new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(StatusConstant.ENABLE);
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.saveType(category);

    }
//分页查询分类
    @Override
    public PageResult queryPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
       Page<Category> page = categoryMapper.queryPage(categoryPageQueryDTO);
        long total = page.getTotal();
        PageResult pageResult =new PageResult(total,page);
        return pageResult;
    }
//    启用,禁用分类

    @Override
    public void startOrStop(Integer status, Long id) {
        Category categroy = Category.builder()
                .id(id)
                .status(status)
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.update(categroy);
    }
//修改分类
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }
//根据类型查询分类
    @Override
    public Category[] queryType(Integer type) {
        Category[] categories =categoryMapper.queryType(type);
        return categories;
    }

    @Override
    public void delete(Long id) {
       Integer count =dishMapper.countByCategoryId(id);
       if(count>0){
           throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
       }
       count=setmealMapper.countByCategoryId(id);
       if (count>0){
           throw  new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
       }
       categoryMapper.delete(id);


    }


}
