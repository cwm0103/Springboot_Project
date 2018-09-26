package com.oper.api;

import com.alibaba.fastjson.JSONObject;
import com.oper.entity.CimIoCode;
import com.oper.service.CimIoCodeService;
import com.oper.util.ForeignResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 创建IO控制器
 */
@RestController
@RequestMapping("/api/cimiocode")
@CrossOrigin
public class CimIoCodeController {


    @Autowired
    private CimIoCodeService cimIoCodeService;

    @ApiOperation(value = "查询IO点", notes = "根据code和name来模糊查询")
    @GetMapping(value = "/getlistByCodeOrName")
    public Object getlistByCodeOrName(String codeName){
        if(codeName==null)
        {
            codeName="";
        }
        try{

            List<Map<String,String>> list=new LinkedList<>();
            List<CimIoCode> listLikeCodeOrName = cimIoCodeService.getListLikeCodeOrName("%"+codeName+"%","%"+ codeName+"%");
            if(listLikeCodeOrName.size()>0)
            {

                listLikeCodeOrName.forEach(ll->{
                    Map<String,String> map=new HashMap<>();
                    map.put("code",ll.getCimCode());
                    map.put("name",ll.getCimName());
                    map.put("unit",ll.getUnit());
                    map.put("objName",ll.getIoCode());
                    list.add(map);
                });

                return ForeignResult.SUCCESS(list);
            }
            return ForeignResult.SUCCESS(list);

        }catch (Exception ex){
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }
    @ApiOperation(value = "查询CIM类及实例对像", notes = "根据实例名来模糊查询")
    @GetMapping(value = "/getCimInstanceList")
    public Object getCimInstanceList(String name){
        try{
          List<JSONObject> result=  cimIoCodeService.getCimInstanceList(name);
            return ForeignResult.SUCCESS(result);
        }catch (Exception ex){
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }

    @ApiOperation(value = "查询CIM类及实例对像", notes = "根据实例名来模糊查询")
    @GetMapping(value = "/getCimCodeByInstance")
    public Object getCimCodeByInstance(String instanceCode){
        try{
            List<CimIoCode> result=  cimIoCodeService.getCimIOCodeByObjectCode(instanceCode);
            return ForeignResult.SUCCESS(result);
        }catch (Exception ex){
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }




}
