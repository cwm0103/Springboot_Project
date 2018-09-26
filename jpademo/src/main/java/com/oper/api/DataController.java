package com.oper.api;

import com.oper.entity.PubDashboardConfig;
import com.oper.entity.PubDashboardData;
import com.oper.entity.vo.DataParmerVO;
import com.oper.service.DataService.DataServiceFactory;
import com.oper.service.PubDashboardConfigService;
import com.oper.service.PubDashboardDataService;
import com.oper.util.ForeignResult;
import com.oper.util.ServiceException;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.DataPropertyDescriptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Auther: jindb
 * @Date: 2018/8/17 09:39
 * @Description:
 */
@RestController
@RequestMapping("/api/data")
@CrossOrigin
@Slf4j
public class DataController {


    @Autowired
    private DataServiceFactory dataServiceFactory;
    @ApiOperation(value = "获取数据", notes = "")
    @PostMapping(value = "/get")
    public Object getData(@RequestBody DataParmerVO prame){
        try{
            return ForeignResult.SUCCESS(dataServiceFactory.getData(prame));
        }catch (ServiceException se){
            return  ForeignResult.FAIL(se.getCode(),se.getMsg());
        }
        catch (Exception ex){
            log.error("error",ex);
            return  ForeignResult.FAIL("500",ex.getMessage());
        }
    }
}
