package com.oper.api;

import com.oper.entity.PubDirectory;
import com.oper.entity.PubView;
import com.oper.service.PubViewService;
import com.oper.util.ForeignResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: jindb
 * @Date: 2018/8/8 11:25
 * @Description:
 */
@RestController
@RequestMapping("/api/view")
public class ViewController {

    @Autowired
    PubViewService pubViewService;

    @ApiOperation(value = "添加视图", notes = "")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody PubView param, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  ForeignResult.FAIL("501",bindingResult.getFieldError().getDefaultMessage());
        }
        try{
       return ForeignResult.SUCCESS();

        }catch (Exception ex){
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "修改视图", notes = "")
    @PostMapping(value = "/modify")
    public Object modify(@Validated @RequestBody PubView param, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  ForeignResult.FAIL("501",bindingResult.getFieldError().getDefaultMessage());
        }
        try{
            return ForeignResult.SUCCESS();

        }catch (Exception ex){
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "删除视图", notes = "")
    @PostMapping(value = "/del")
    public Object del(Integer id){

        try{
            return ForeignResult.SUCCESS();

        }catch (Exception ex){
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }
}

