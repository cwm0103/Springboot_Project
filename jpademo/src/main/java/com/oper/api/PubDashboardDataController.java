package com.oper.api;

import com.oper.dto.DashboardDataDTO;
import com.oper.entity.PubDashboardConfig;
import com.oper.entity.PubDashboardData;
import com.oper.service.PubDashboardDataService;
import com.oper.util.ForeignResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * cwm
 * add
 * 2018-8-8
 * 数据控制器
 */
@RestController
@RequestMapping("/api/pubdashboarddata")
@CrossOrigin
public class PubDashboardDataController {

    @Autowired
    private PubDashboardDataService pubDashboardDataService;

    @ApiOperation(value = "添加数据配置信息", notes = "")
    @PostMapping("add")
    public Object add(@Validated @RequestBody PubDashboardData pdhd, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ForeignResult.FAIL("501",bindingResult.getFieldError().getDefaultMessage());
        }
        try
        {
            PubDashboardData pubDashboardData = pubDashboardDataService.add(pdhd);
            if(pubDashboardData!=null)
            {
                return ForeignResult.SUCCESS();
            }

        }catch (Exception ex)
        {
            return ForeignResult.FAIL("添加失败",ex.getMessage());
        }
        return ForeignResult.FAIL();
    }

    @ApiOperation(value = "删除数据配置",notes = "根据Id来删除")
    @DeleteMapping("del")
    public Object del(Integer id)
    {
        try{
            if(pubDashboardDataService.del(id))
            {
                return ForeignResult.SUCCESS();
            }
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "修改数据配置",notes = "")
    @PostMapping("update")
    public Object update(@Validated @RequestBody PubDashboardData pdhd)
    {
        try{
            if(pubDashboardDataService.update(pdhd))
            {
                return ForeignResult.SUCCESS();
            }
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "查询符合条件的报表数据",notes = "根据did来查询数据")
    @GetMapping("getListByDid")
    public Object getListByDid(Integer did)
    {
        try{
            List<DashboardDataDTO> listByDid = pubDashboardDataService.getDtoListByDid(did);
            if(listByDid!=null)
            {
                return ForeignResult.SUCCESS(listByDid);
            }
            return ForeignResult.FAIL();
        }catch (Exception ex){
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "根据Id获取数据实体",notes = "根据Id 获取数据实体")
    @GetMapping("getById")
    public Object getById(Integer id)
    {
        try{
            PubDashboardData pubDashboardData = pubDashboardDataService.getPubDashboardDataById(id);
            return ForeignResult.SUCCESS(pubDashboardData);
        }catch (Exception ex){
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }

}
