package com.oper.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.oper.entity.PubDashboardConfig;
import com.oper.service.PubDashboardConfigService;
import com.oper.util.ForeignResult;
import io.swagger.annotations.ApiOperation;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * cwm
 * add
 * 2018-8-8
 * 图表配置控制器
 */
@RestController
@RequestMapping("/api/pubdashboard")
@CrossOrigin
public class PubDashboardConfigController {


    @Autowired
    private PubDashboardConfigService pubDashboardConfigService;


    @ApiOperation(value = "添加图表配置信息", notes = "")
    @PostMapping("add")
    public Object add(@Validated @RequestBody PubDashboardConfig pubDashboardConfig, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ForeignResult.FAIL("501",bindingResult.getFieldError().getDefaultMessage());
        }
        try
        {
            PubDashboardConfig dashboardConfig = pubDashboardConfigService.add(pubDashboardConfig);
            if(dashboardConfig!=null)
            {
                return ForeignResult.SUCCESS(dashboardConfig.getDashboardId());
            }

        }catch (Exception ex)
        {
            return ForeignResult.FAIL("添加失败",ex.getMessage());
        }
        return ForeignResult.FAIL();
    }

    @ApiOperation(value = "删除图表配置",notes = "根据Id来删除")
    @DeleteMapping("del")
    public Object del(Integer id)
    {
        try{
            if(pubDashboardConfigService.delete(id))
            {
                return ForeignResult.SUCCESS();
            }
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "修改图表配置",notes = "")
    @PostMapping("update")
    public Object update(@Validated @RequestBody PubDashboardConfig pbc)
    {
        try{
            if(pubDashboardConfigService.update(pbc))
            {
                return ForeignResult.SUCCESS();
            }
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "获取视图组合数据",notes = "根据视图vid来获取")
    @GetMapping("getAllentity")
    public Object getAllentity(Integer vid)
    {
        try{
            Object alllist = pubDashboardConfigService.getAlllist(vid);
            if(alllist!=null) return ForeignResult.SUCCESS(alllist);
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "获取视图组合数据单条",notes = "根据视图did来获取")
    @GetMapping("getOneEntity")
    public Object getOneEntity(Integer did)
    {
        try{
            Object alllist = pubDashboardConfigService.getOnelist(did);
            if(alllist!=null) return ForeignResult.SUCCESS(alllist);
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }


    @ApiOperation(value = "获取报表Id",notes = "根据报表标题和类型，还有描述")
    @PostMapping("getPid")
    public Object getPid(@RequestBody PubDashboardConfig pdc)
    {
        try{
            PubDashboardConfig pubDcByTTD = pubDashboardConfigService.getPubDcByTTD(pdc);
            if(pubDcByTTD!=null)return ForeignResult.SUCCESS(pubDcByTTD.getDashboardId());
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "修改报表位置",notes = "根据报表id")
    @GetMapping("updPositionById")
    public Object updatePositionById(Integer id,String position)
    {
        try{
            Boolean aBoolean = pubDashboardConfigService.udatePosition(id, position);
            if(aBoolean) return ForeignResult.SUCCESS();
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "查询一条报表数据",notes = "根据报表id")
    @GetMapping("getById")
    public Object getById(Integer id)
    {
        try{
            PubDashboardConfig byId = pubDashboardConfigService.getById(id);
            if(byId!=null)
            {
                return ForeignResult.SUCCESS(byId);
            }
            return ForeignResult.SUCCESS(0);
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "查询所有得报表数据",notes = "根据视图vid")
    @GetMapping("getAllPDCByVid")
    public Object getAllPDCByVid(Integer vid)
    {
            try{
                List<PubDashboardConfig> pdcListByVid = pubDashboardConfigService.getPDCListByVid(vid);
                if(pdcListByVid!=null)
                {
                    return ForeignResult.SUCCESS(pdcListByVid);
                }
                return ForeignResult.FAIL();
            }catch (Exception ex)
            {
                return ForeignResult.FAIL("500",ex.getMessage());
            }
    }
    @ApiOperation(value = "批量修改视图位置",notes = "根据报表id和位置")
    @PostMapping("updByPosition")
    public Object updByPosition(@RequestBody String pdclist)
    {
        try{

            JSONObject jsonObject= JSONObject.parseObject(pdclist);
            JSONArray pdclists = jsonObject.getJSONArray("pdclist");

            if(pdclists.size()>0)
            {
                if(pubDashboardConfigService.updateListPosition(pdclists))
                {
                    return ForeignResult.SUCCESS();
                }

            }
            return ForeignResult.FAIL();

        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }


}
