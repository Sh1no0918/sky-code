package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "套餐相关接口")
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
@PostMapping
@ApiOperation("新增套餐")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO){
    setmealService.addSetmeal(setmealDTO);
    return Result.success();
}
@GetMapping("/page")
@ApiOperation("分页查询套餐")
public Result<PageResult> queryByPage(SetmealPageQueryDTO setmealPageQueryDTO){
PageResult pageResult=setmealService.queryByPage(setmealPageQueryDTO);
return Result.success(pageResult);
}
@PostMapping("/status/{status}")
@ApiOperation("套餐起售,停售")
public Result startOrStop(@PathVariable Integer status ,Long id){
    setmealService.startOrStop(status,id);
    return Result.success();
}
@GetMapping("/{id}")
@ApiOperation("根据id查询套餐")
public Result<SetmealVO>queryById(@PathVariable Long id){
   SetmealVO setmealVO= setmealService.queryById(id);
   return Result.success(setmealVO);
}
@PutMapping
@ApiOperation("修改套餐")
public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
setmealService.updateSetmeal(setmealDTO);
return Result.success();
}
@DeleteMapping
@ApiOperation("批量删除套餐")
public  Result deleteSetmeal(@RequestParam List<Long> ids){
    setmealService.deleteSetmeal(ids);
    return Result.success();
}


}
