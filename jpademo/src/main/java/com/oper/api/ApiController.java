package com.oper.api;

import com.alibaba.fastjson.JSONObject;
import com.oper.util.ForeignResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * api接口
 */
@RestController
@RequestMapping("/api")
public class ApiController {






    /**
     * 单位耗能
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取单位耗能列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param",value = "{\"stationId\":\"CA02ES01\"}", required = true, paramType = "json "),
    })
    @RequestMapping(value = "/consumeenergy", method = {RequestMethod.POST})
    public Object ConsumeEnergy(@RequestBody Map<String, String> param) throws Exception {
        String stationId = param.get("stationId");
        JSONObject responseJson = new JSONObject();
        return  ForeignResult.SUCCESS();
    }



}
