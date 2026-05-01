package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

public interface CategoryService {
    void saveType(CategoryDTO categoryDTO);

    PageResult queryPage(CategoryPageQueryDTO categoryPageQueryDTO);

    void startOrStop(Integer status, Long id);


    void update(CategoryDTO categoryDTO);

    Category[] queryType(Integer type);

    void delete(Long id);
}
