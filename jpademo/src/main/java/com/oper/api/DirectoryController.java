package com.oper.api;

import com.oper.dto.PubDirectoryVModel;
import com.oper.entity.PubDirectory;
import com.oper.service.PubDirectoryService;
import com.oper.util.ForeignResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Auther: jindb
 * @Date: 2018/8/7 15:41
 * @Description:
 */
@RestController
@RequestMapping("/api/directory")
@CrossOrigin
public class DirectoryController {

    @Autowired
    PubDirectoryService _pubDirectoryService;

    @ApiOperation(value = "添加目录", notes = "")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody  PubDirectory param, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  ForeignResult.FAIL("501",bindingResult.getFieldError().getDefaultMessage());
        }
        try{
            return ForeignResult.SUCCESS(_pubDirectoryService.add(param));
        }catch (Exception ex){
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "修改目录", notes = "")
    @PostMapping(value = "/modify")
    public Object modify(@Validated @RequestBody  PubDirectory param, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  ForeignResult.FAIL("500",bindingResult.getFieldError().getDefaultMessage());
        }
        try{
            return ForeignResult.SUCCESS(_pubDirectoryService.update(param));
        }catch (Exception ex){
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "移动目录", notes = "")
    @PostMapping(value = "/move")
    public Object move(Integer pid,Integer id){
        return ForeignResult.SUCCESS(_pubDirectoryService.move(pid,id));
    }

    @ApiOperation(value = "获取所有目录", notes = "")
    @GetMapping(value = "/getAll")
    public Object getAll(){
        return ForeignResult.SUCCESS(_pubDirectoryService.findAll());
    }
    @ApiOperation(value = "删除目录", notes = "")
    @DeleteMapping(value = "/del")
    public Object del(Integer id){
        return ForeignResult.SUCCESS(_pubDirectoryService.del(id));
    }

    @ApiOperation(value = "获取一个目录",notes = "根据id来获取一个目录")
    @GetMapping("getOne")
    public Object getByidOne(Integer id)
    {
        try{
            PubDirectory byidOne = _pubDirectoryService.getByidOne(id);
            if(byidOne!=null)
            {
                return ForeignResult.SUCCESS(byidOne);
            }
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    /**
     * 获取所有的目录数和下面的视图
     * @return
     */
    @ApiOperation(value = "获取所有的目录数和下面的视图",notes = "没有参数")
    @GetMapping("getAllDire")
    public Object getAllDirList()
    {
        try{
            List<PubDirectoryVModel> allDirList = _pubDirectoryService.getAllDirList();
            if(allDirList!=null)
            {
                return ForeignResult.SUCCESS(allDirList);
            }
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }







}
