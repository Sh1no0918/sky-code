package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryController {
    @Autowired
   private CategoryService categoryService;
@PostMapping
@ApiOperation("新增分类")
public Result saveType(@RequestBody CategoryDTO categoryDTO){
    categoryService.saveType(categoryDTO);
    return Result.success();
}
@GetMapping("/page")
@ApiOperation("分类分页查询")
public Result<PageResult> queryPage(CategoryPageQueryDTO categoryPageQueryDTO){
    PageResult pageResult=categoryService.queryPage(categoryPageQueryDTO);
    return Result.success(pageResult);
}
@PostMapping("/status/{status}")
@ApiOperation("启用,禁用分类")
public Result startOrStop(@PathVariable  Integer status ,Long id){
    categoryService.startOrStop(status,id);
    return Result.success();
}
@PutMapping
@ApiOperation("修改分类")
public Result update(@RequestBody CategoryDTO categoryDTO){
categoryService.update(categoryDTO);
return Result.success();
}
@GetMapping("/list")
@ApiOperation("根据类型查询分类")
public Result<Category[]> queryType(Integer type){
    Category[] categories= categoryService.queryType(type);
    return Result.success(categories);
}
@DeleteMapping
@ApiModelProperty("根据ID删除分类")
public Result delete(Long id){
    categoryService.delete(id);
    return Result.success();
}


}
