package com.oper.api;

import com.oper.entity.PubAxisConfig;
import com.oper.entity.PubDashboardConfig;
import com.oper.service.PubAxisConfigService;
import com.oper.util.ForeignResult;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/axisconfig")
@CrossOrigin
public class PubAxisConfigController {

    @Autowired
    private PubAxisConfigService pubAxisConfigService;

    @ApiOperation(value = "删除数据",notes = "根据主键Id来删除")
    @DeleteMapping("del")
    public Object del(Integer id)
    {
        try{
            if(pubAxisConfigService.DeleteById(id))
            {
                return ForeignResult.SUCCESS();
            }
            return ForeignResult.SUCCESS();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "添加一条数据",notes = "")
    @PostMapping("add")
    public Object add(@Valid @RequestBody PubAxisConfig pubAxisConfig, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ForeignResult.FAIL("501",bindingResult.getFieldError().getDefaultMessage());
        }
            try{
                PubAxisConfig add = pubAxisConfigService.add(pubAxisConfig);
                if(add!=null)
                {
                    return ForeignResult.SUCCESS(add);
                }
                return ForeignResult.FAIL();
            }catch (Exception ex)
            {
                return ForeignResult.FAIL("500",ex.getMessage());
            }
    }
    @ApiOperation(value = "修改一条数据",notes = "")
    @PostMapping("upd")
    public Object upd(@RequestBody PubAxisConfig pubAxisConfig)
    {
        try{
            Boolean update = pubAxisConfigService.update(pubAxisConfig);
            if(update)return ForeignResult.SUCCESS();
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "获取所有的坐标轴" ,notes = "根据dashboard_id")
    @GetMapping("getListByDid")
    public Object getListByDid(Integer did)
    {
        try
        {
            List<PubAxisConfig> listByDid = pubAxisConfigService.getListByDid(did);
            return ForeignResult.SUCCESS(listByDid);
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "获取一条数据", notes = "根据主键Id")
    @GetMapping("getByIdOne")
    public Object getByIdOne(Integer id)
    {
        try{
            PubAxisConfig byIdOne = pubAxisConfigService.getByIdOne(id);
            return ForeignResult.SUCCESS(byIdOne);
        }catch (Exception ex){
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }
}
