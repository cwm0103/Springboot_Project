package com.oper.api;

import com.oper.dto.TimeTypesDTO;
import com.oper.entity.PubView;
import com.oper.service.PubViewService;
import com.oper.util.ForeignResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * cwm
 * add
 * 2018-8-8
 * 视图控制器API
 */
@RestController
@RequestMapping("/api/pubview")
@CrossOrigin
public class PubViewController {

    @Autowired
    private PubViewService pubViewService ;


    @ApiOperation(value = "添加视图",notes = "")
    @PostMapping("add")
    public Object add(@Validated @RequestBody PubView pvw, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ForeignResult.FAIL("501",bindingResult.getFieldError().getDefaultMessage());
        }
        try{
            PubView add = pubViewService.add(pvw);
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

    @ApiOperation(value = "删除视图",notes = "")
    @DeleteMapping("del")
    public Object del(Integer id)
    {
        try {
            if(pubViewService.del(id))
            {
                return ForeignResult.SUCCESS();
            }
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "修改视图",notes = "")
    @PostMapping("update")
    public Object update(@Validated @RequestBody PubView pvw,BindingResult bindingResult)
    {
            if(bindingResult.hasErrors())
            {
                return ForeignResult.FAIL("501",bindingResult.getFieldError().getDefaultMessage());
            }

            try {
                if(pubViewService.update(pvw))
                {
                    return ForeignResult.SUCCESS();
                }
                return ForeignResult.FAIL();

            }catch (Exception ex)
            {
                return ForeignResult.FAIL("500",ex.getMessage());
            }
    }
    @ApiOperation(value = "获取一个视图",notes = "根据主键Id来获取一个视图")
    @PostMapping("selectOne")
    public Object selectOneById(Integer id)
    {
        try{
            PubView pubViewByIdOne = pubViewService.getPubViewByIdOne(id);
            if(pubViewByIdOne!=null)return ForeignResult.SUCCESS(pubViewByIdOne);
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }

    }
    @ApiOperation(value = "修改一个视图的目录",notes = "根据视图vid和目录did(最终目录did)来修改视图数据")
    @GetMapping("updViewDid")
    public Object updateViewDid(Integer vid,Integer did)
    {
        try
        {
            Boolean aBoolean = pubViewService.uodatePid(vid, did);
            if(aBoolean) return  ForeignResult.SUCCESS();
            return ForeignResult.FAIL();
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "查找",notes = "根据报表id")
    @GetMapping("findTimeType")
    public Object findTimeType(Integer did)
    {
        try{
            PubView byId = pubViewService.findTimeTypesByDid(did);
            if(byId!=null)
            {
                TimeTypesDTO dto = new TimeTypesDTO() ;
                dto.setDashboardType(byId.getViewDesc());
                dto.setViewType(byId.getTimeType());
                return ForeignResult.SUCCESS(dto);
            }
            return ForeignResult.SUCCESS(0);
        }catch (Exception ex)
        {
            return ForeignResult.FAIL("500",ex.getMessage());
        }
    }
}
