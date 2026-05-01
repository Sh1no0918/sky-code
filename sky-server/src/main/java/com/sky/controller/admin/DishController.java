package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody  DishDTO dishDTO){
        dishService.addDish(dishDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> queryPageDish(DishPageQueryDTO dishPageQueryDTO){
             PageResult pageResult =dishService.queryPageDish(dishPageQueryDTO);
             return Result.success(pageResult);
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> queryById(@PathVariable Long id){
       DishVO dishVO = dishService.queryById(id);
       return Result.success(dishVO);
    }
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result queryByCategoryController(Long categoryId){
        Dish[] dishes =dishService.queryByCatrgoryController(categoryId);
        return Result.success(dishes);

    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售,停售")
    public Result startOrStop(@PathVariable Integer status,Long id){
        dishService.startOrStop(status,id);
        return Result.success();
    }
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);
        return Result.success();
    }
@DeleteMapping
@ApiOperation("批量删除菜品")
    public Result deleteDishes(@RequestParam List<Long> ids){
        dishService.delete(ids);
        return  Result.success();
}



}
