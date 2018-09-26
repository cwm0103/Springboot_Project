package com.oper.service.DataService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oper.entity.PubDashboardConfig;
import com.oper.entity.PubDashboardData;
import com.oper.entity.vo.DataParmerVO;
import com.oper.service.PubDashboardConfigService;
import com.oper.service.PubDashboardDataService;
import com.oper.util.HTTPUtils;
import com.oper.util.RestTemplateUtils;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: jindb
 * @Date: 2018/8/17 10:05
 * @Description:
 */
@Service
@Slf4j
public class DataServiceFactory {
    @Autowired
    ApplicationContext context;
    @Autowired
    private PubDashboardConfigService pubDashboardConfigService;

    @Autowired
    private PubDashboardDataService pubDashboardDataService;

    private static final Map<String,String> mapDateType;

    static {
        Map aMap = new HashMap();
        aMap.put("MI", "MI");
        aMap.put("HH", "Hour");
        aMap.put("DD", "Day");
        aMap.put("MM", "Month");
        aMap.put("YY", "Year");
        mapDateType = Collections.unmodifiableMap(aMap);
    }
    //@Autowired
   // RestTemplate restTemplate;
    @Value("${web.dataurl}")
    private String restUrl;

    public Map<String,Object> getData(DataParmerVO prame) throws Exception {
        PubDashboardConfig pubDashboardConfig= pubDashboardConfigService.getById(prame.getDashboardId());

        String beanType=pubDashboardConfig.getChartType();
        if(pubDashboardConfig.getChartType().equals("bar") || pubDashboardConfig.getChartType().equals("wire")){
            beanType="line";
        }

        if(pubDashboardConfig.getChartType().equals("ring")){
            beanType="pie";
        }
        List<PubDashboardData> pubDashboardDataList= pubDashboardDataService.getListByDid(prame.getDashboardId());
        List<String> codes=pubDashboardDataList.stream().map(x->x.getCode()).collect(Collectors.toList());
        JSONObject postData = new JSONObject();
        postData.put("beginDate",prame.getBeginDate());
        postData.put("endDate",prame.getEndDate());
        String dateType=mapDateType.get(prame.getDateType());
        postData.put("dateType",dateType);
        postData.put("codes",codes);
        log.info("参数："+JSONObject.toJSONString(prame));
        log.info("请求参数："+postData.toJSONString());
       // String  response1 = RestTemplateUtils.post(restUrl,postData,MediaType.APPLICATION_JSON);
        JSONObject json= null;
        if(StringUtils.isNotBlank(restUrl))
        {
           json= HTTPUtils.post(restUrl,postData.toJSONString());
            log.info("结果："+json.toJSONString());
        }

        JSONArray datajson=null==json?null:json.getJSONArray("data");
        IDataService dataService=(IDataService)context.getBean(beanType);
        return dataService.getData(pubDashboardConfig,pubDashboardDataList,datajson, prame);
    }


}
