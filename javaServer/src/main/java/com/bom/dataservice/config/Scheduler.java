package com.bom.dataservice.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bom.dataservice.model.AjaxResult;
import com.bom.dataservice.model.EquInfo;
import com.bom.dataservice.service.DayDataSpvService;
import com.bom.dataservice.service.EquInfoService;
import com.bom.dataservice.service.HourdataSpvService;
import com.bom.dataservice.service.MonthDataSpvService;
import com.bom.dataservice.utils.HttpClientHelper;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenwangming on 2017/11/18.
 */
@Component
public class Scheduler {

    @Autowired
    private EquInfoService equInfoService;
    @Autowired
    private HourdataSpvService hourdataSpvService;
    @Autowired
    private DayDataSpvService dayDataSpvService;
    @Autowired
    private MonthDataSpvService monthDataSpvService;
    //光伏数据接口url
    @Value("${PV.url}")
    private String  pvurl;

//    //小时定时数据执行
//    @Scheduled(cron="0 0/2 * * * ?") //每分钟执行一次
//    public void hourTasks() throws JSONException, IllegalAccessException {
//        System.out.println("每2分钟执行一次。开始……");
//        //定时执行任务
//        getHourYield("2022",35,new Date());
//        System.out.println("每2分钟执行一次。结束。");
//    }
//
//    //日定时数据执行
//    @Scheduled(fixedRate=20000)
//    public void dayTasks() throws JSONException, IllegalAccessException {
//        System.out.println("每1小时执行一次。开始……");
//        //定时执行任务
//        getDayYield("2022","DailyYield",new Date());
//        System.out.println("每1小时执行一次。结束。");
//    }
//    //月定时数据执行
//    @Scheduled(fixedRate=20000)
//    public void monthTasks() throws JSONException, IllegalAccessException {
//        System.out.println("每10小时执行一次。开始……");
//        //定时执行任务
//
//
//        System.out.println("每10小时执行一次。结束。");
//    }
//    //年定时数据执行
//    @Scheduled(fixedRate=20000)
//    public void yearTasks() throws JSONException, IllegalAccessException {
//        System.out.println("每10天执行一次。开始……");
//        //定时执行任务
//
//
//        System.out.println("每10天执行一次。结束。");
//    }


    /**
     *  获取小时任务
     * @param station_id
     * @param equ_category_big
     * @param datetime
     * @throws JSONException
     * @throws IllegalAccessException
     */
    public void getHourYield(String station_id,Integer equ_category_big,Date datetime) throws JSONException, IllegalAccessException//@RequestBody Map<String, String> param
    {
        double lastValue=0;
        //获取生备
        List<EquInfo> listEquInfo = null;//Integer.parseInt(equ_category_big)
        try {
            listEquInfo = equInfoService.getListEquInfo(station_id, equ_category_big);
            String deviceNoList="";
            for (EquInfo item:listEquInfo) {
                deviceNoList+="\""+item.getEqu_name()+"\",";
            }
            if(deviceNoList.length()>0)
            {
                deviceNoList=deviceNoList.substring(0,deviceNoList.length()-1);
            }
            //准备参数
            String pattan="yyyy-MM-dd HH:00:00";
            String pattern="yyyy-MM-dd HH:59:59";
            SimpleDateFormat sdf = new SimpleDateFormat(pattan);
            SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);

            String JsonParam="{"+
                    "\"startTime\":\""+sdf.format(datetime)+"\","+
                    "\"endTime\":\""+sdf1.format(datetime)+"\","+
                    "\"metricList\": [\"cDailyYield\"],"+
                    "\"deviceNoList\":["+deviceNoList+"] "+
                    "}";
            JSONObject parse = JSONObject.parseObject(JsonParam);
            JSONObject jsonObject = HttpClientHelper.httpPostPV(pvurl, parse, false);
            JSONArray dataList = jsonObject.getJSONArray("dataList");
            List<Map<String, Object>> list = new LinkedList<>();
            if(dataList.size()>0)
            {
                for(int i=0;i<dataList.size();i++)
                {
                    JSONObject emp = dataList.getJSONObject(i);
                    lastValue+= (Double.parseDouble((String) emp.get("lastValue"))-Double.parseDouble((String)emp.get("firstValue"))) ;
                }
            }
            //return new AjaxResult().success(lastValue);
            ParsePosition pos=new ParsePosition(0);
            Date time= sdf.parse(sdf.format(datetime),pos);
            //保存到数据库小时值
            hourdataSpvService.InsertOrUpdatedataSpv(station_id,"HourYield",time,lastValue);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            //return new AjaxResult().failure();
        }
    }

    /**
     * 获取日定时任务
     * @param station_id
     * @param code
     * @param date
     */
    public void getDayYield(String station_id ,String code,Date date)
    {
        String pattan="yyyy-MM-dd 00:00:00";
        String pattern="yyyy-MM-dd 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat(pattan);
        SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);
        ParsePosition pos=new ParsePosition(0);
        Date begintime= sdf.parse(sdf.format(date),pos);
        Date endtime= sdf.parse(sdf1.format(date),pos);
        dayDataSpvService.InsertOrUpdatedataSpv(station_id,code,begintime,endtime);
    }

    /**
     * 获取月定时任务
     * @param station_id
     * @param code
     * @param date
     */
    public void getMonthYield(String station_id,String code,Date date)
    {
        String pattan="yyyy--01 00:00:00";
        String pattern="yyyy-MM-dd 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat(pattan);
        SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);
        ParsePosition pos=new ParsePosition(0);
        Date begintime= sdf.parse(sdf.format(date),pos);
        Date endtime= sdf.parse(sdf1.format(date),pos);
        monthDataSpvService.InsertOrUpdatedataSpv(station_id,code,begintime,endtime);
    }
}
