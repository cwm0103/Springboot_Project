package com.oper.service.DataService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oper.entity.PubAxisConfig;
import com.oper.entity.PubDashboardConfig;
import com.oper.entity.PubDashboardData;
import com.oper.entity.vo.DataParmerVO;
import com.oper.repository.PubAxisConfigRepository;
import com.oper.service.PubDashboardDataService;
import com.oper.util.DataUtil;
import com.oper.util.DateUtil;
import com.oper.util.MathUtil;
import com.oper.util.ServiceException;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.rmi.ServerException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: jindb
 * @Date: 2018/8/17 10:05
 * @Description:
 */
@Component("line")
@Slf4j
public class LineDataService implements IDataService {


    @Autowired
    private PubDashboardDataService pubDashboardDataService;
    @Autowired
    private PubAxisConfigRepository pubAxisConfig;

  //  @Autowired
   // private RestTemplate restTemplate;

    public Map<String,Object> getData(PubDashboardConfig config,List<PubDashboardData> pubDashboardDataList,JSONArray jsonObject,DataParmerVO prame) throws Exception {
        return  getWCData(config,pubDashboardDataList,jsonObject,prame);
     /*   if(null==jsonObject){
            return  getWCData(config,pubDashboardDataList,jsonObject,prame);
        }else{
            return  getData(config,pubDashboardDataList,jsonObject,prame,false);
        }*/
    }

    /**
     * 根据超总需要重写数据方法
     * @param config
     * @param pubDashboardDataList
     * @param jsonObject
     * @param prame
     * @return
     */
    private  Map<String, Object> getWCData(PubDashboardConfig config,List<PubDashboardData> pubDashboardDataList,JSONArray jsonObject, DataParmerVO prame){
        Map<String,Object> reslut=new HashMap<>();
        //X轴设置
        List<Map<String,Object>> xAxisList=new ArrayList<>();
        List<String> xdata=DateUtil.findDates(prame.getBeginDate(),prame.getEndDate(),prame.getDateType());
        Map<String,Object> xAxis=new HashMap<>();
        xAxis.put("type","category");
        xAxis.put("data",xdata);
        xAxisList.add(xAxis);

        //数据
        if(pubDashboardDataList!=null && pubDashboardDataList.size()>0){
            Map<String,Integer> mapUnit=new LinkedHashMap<>();
            int axisIndex=0;
            List<Map<String,Object>> series=new ArrayList<>();
            Map<String,List<Object>>  mapData;
            if(null ==jsonObject){
                mapData= null;
            }else{
                mapData= DataUtil.paseData(jsonObject);
            }

            List<String> legend= new LinkedList<>();

            for (PubDashboardData pubDashboardData :pubDashboardDataList) {
                //获取单位
                if(StringUtils.isNotBlank(pubDashboardData.getUnit()) && !mapUnit.containsKey(pubDashboardData.getUnit())){
                    mapUnit.put(pubDashboardData.getUnit(),axisIndex);
                    axisIndex+=1;
                }

                Map<String,Object> serie=new HashMap<>();
                serie.put("name",pubDashboardData.getDataName());
                serie.put("type",pubDashboardData.getChartType());
                serie.put("color",pubDashboardData.getColor());
                serie.put("yAxisIndex",mapUnit.get(pubDashboardData.getUnit()));
                List<Object> datas;
                if(null == mapData){
                    datas=new LinkedList<>();
                    for (String date: xdata) {
                        datas.add(MathUtil.GetevenNum(0,1000));
                    }
                }else{
                    datas=mapData.get(pubDashboardData.getCode());
                }
                serie.put("data",datas);
                series.add(serie);
                legend.add(pubDashboardData.getDataName());
            }
            //y轴设置
            List<Map<String,Object>> YAxisList=new LinkedList<>();
            for (String key:mapUnit.keySet()) {
                Map<String,Object> yAxis=new HashMap<>();
                if(mapUnit.size()>2){
                    yAxis.put("type","value");
                    yAxis.put("show",false);
                }else{
                    yAxis.put("type","value");
                    Map<String,String> formatter=new HashMap<>();
                    formatter.put("formatter","{value}"+key);
                    yAxis.put("axisLabel",formatter);
                }
                YAxisList.add(yAxis);
            }
            reslut.put("title",config.getDashboardTitle());
            reslut.put("legend",legend);
            reslut.put("xAxis",xAxisList);
            reslut.put("yAxis",YAxisList);
            reslut.put("series",series);
        }else{
            throw new ServiceException("501","没有设置数据");
        }
        return reslut;
    }


    private Map<String, Object> getData(PubDashboardConfig config,List<PubDashboardData> pubDashboardDataList,JSONArray jsonObject, DataParmerVO prame,boolean isTest){
        Map<String,Object> reslut=new HashMap<>();
       // List<PubDashboardData> pubDashboardDataList= pubDashboardDataService.getListByDid(prame.getDashboardId());

        if(pubDashboardDataList!=null && pubDashboardDataList.size()>0){
            //获取轴配置信息
            List<PubAxisConfig> pubAxisConfigs= pubAxisConfig.findByDashboardId(prame.getDashboardId());
            //获取X轴配置
            List<PubAxisConfig> xAxisConfigs=pubAxisConfigs.stream().filter(x->x.getXory().toLowerCase().equals("x")).collect(Collectors.toList());
            List<Map<String,Object>> xAxisList=new ArrayList<>();
            //x轴数据
            List<String> xdata=DateUtil.findDates(prame.getBeginDate(),prame.getEndDate(),prame.getDateType());
            if(null !=xAxisConfigs &&  xAxisConfigs.size()>0){
                for (PubAxisConfig pubAxisConfig: xAxisConfigs) {
                    Map<String,Object> xAxis=new HashMap<>();
                    String type=pubAxisConfig.getAxisType();
                    xAxis.put("type",type);
                    if(type.equals("category")){
                        xAxis.put("data",xdata);
                    }
                    xAxisList.add(xAxis);
                }
            }else {
                Map<String,Object> xAxis=new HashMap<>();
                xAxis.put("type","category");
                xAxis.put("data",xdata);
                xAxisList.add(xAxis);
            }
            //获取Y轴配置
            List<PubAxisConfig> yAxisConfigs=pubAxisConfigs.stream().filter(x->x.getXory().toLowerCase().equals("y")).collect(Collectors.toList());
            List<Map<String,Object>> YAxisList=new ArrayList<>();
            if(null !=yAxisConfigs &&  yAxisConfigs.size()>0){
                for (PubAxisConfig pubAxisConfig: yAxisConfigs) {
                    Map<String,Object> yAxis=new HashMap<>();
                    String type=pubAxisConfig.getAxisType();
                    yAxis.put("type",type);
                    /*if(type.equals("category")){
                        xAxis.put("data",xdata);
                    }*/
                    yAxis.put("name",pubAxisConfig.getAxisName());
                    yAxis.put("min",pubAxisConfig.getAxisMin());
                    yAxis.put("max",pubAxisConfig.getAxisMax());
                    yAxis.put("interval",pubAxisConfig.getAxisInterval());
                    Short offset= pubAxisConfig.getAxisOffset();
                    String postion=pubAxisConfig.getAxisPosition();
                    if(StringUtils.isNotEmpty(postion))
                        yAxis.put("position",pubAxisConfig.getAxisOffset());
                    if(offset!=null && offset>0)
                        yAxis.put("offset",pubAxisConfig.getAxisOffset());

                    YAxisList.add(yAxis);
                }
            }else {
                Map<String,Object> xAxis=new HashMap<>();
                xAxis.put("type","value");
                YAxisList.add(xAxis);
            }


            //坐标显示
            List<String> legend=pubDashboardDataList.stream().map(x->x.getDataName()).collect(Collectors.toList());
            List<Map<String,Object>> series=new ArrayList<>();
            Map<String,List<Object>> mapData=null;
            if(!isTest){
                mapData= DataUtil.paseData(jsonObject);
            }
            for (PubDashboardData pubDashboardData :pubDashboardDataList) {
                Map<String,Object> serie=new HashMap<>();
                serie.put("name",pubDashboardData.getDataName());
                serie.put("type",pubDashboardData.getChartType());
                serie.put("color",pubDashboardData.getColor());
                List<Object> datas=new ArrayList<>();
                if(isTest)
                {
                    for (String date: xdata) {
                        datas.add(MathUtil.GetevenNum(0,1000));
                    }
                }else{
                    datas=mapData.get(pubDashboardData.getCode());
                }
                serie.put("data",datas);
                series.add(serie);
            }
            reslut.put("title",config.getDashboardTitle());
            reslut.put("legend",legend);
            reslut.put("xAxis",xAxisList);
            reslut.put("yAxis",YAxisList);
            reslut.put("series",series);

        }else{
            throw new ServiceException("501","没有设置数据");
        }
        return  reslut;
    }
    private Map<String, Object> getTestData(PubDashboardConfig config, DataParmerVO prame){
        Map<String,Object> reslut=new HashMap<>();
        List<PubDashboardData> pubDashboardDataList= pubDashboardDataService.getListByDid(prame.getDashboardId());

        if(pubDashboardDataList!=null && pubDashboardDataList.size()>0){
            //获取轴配置信息
            List<PubAxisConfig> pubAxisConfigs= pubAxisConfig.findByDashboardId(prame.getDashboardId());
            //获取X轴配置
            List<PubAxisConfig> xAxisConfigs=pubAxisConfigs.stream().filter(x->x.getXory().toLowerCase().equals("x")).collect(Collectors.toList());
            List<Map<String,Object>> xAxisList=new ArrayList<>();
            //x轴数据
            List<String> xdata=DateUtil.findDates(prame.getBeginDate(),prame.getEndDate(),prame.getDateType());
            if(null !=xAxisConfigs &&  xAxisConfigs.size()>0){
                for (PubAxisConfig pubAxisConfig: xAxisConfigs) {
                    Map<String,Object> xAxis=new HashMap<>();
                    String type=pubAxisConfig.getAxisType();
                    xAxis.put("type",type);
                    if(type.equals("category")){
                        xAxis.put("data",xdata);
                    }
                    xAxisList.add(xAxis);
                }
            }else {
                Map<String,Object> xAxis=new HashMap<>();
                xAxis.put("type","category");
                xAxis.put("data",xdata);
                xAxisList.add(xAxis);
            }
            //获取Y轴配置
            List<PubAxisConfig> yAxisConfigs=pubAxisConfigs.stream().filter(x->x.getXory().toLowerCase().equals("y")).collect(Collectors.toList());
            List<Map<String,Object>> YAxisList=new ArrayList<>();
            if(null !=yAxisConfigs &&  yAxisConfigs.size()>0){
                for (PubAxisConfig pubAxisConfig: yAxisConfigs) {
                    Map<String,Object> yAxis=new HashMap<>();
                    String type=pubAxisConfig.getAxisType();
                    yAxis.put("type",type);
                    /*if(type.equals("category")){
                        xAxis.put("data",xdata);
                    }*/
                    yAxis.put("name",pubAxisConfig.getAxisName());
                    yAxis.put("min",pubAxisConfig.getAxisMin());
                    yAxis.put("max",pubAxisConfig.getAxisMax());
                    yAxis.put("interval",pubAxisConfig.getAxisInterval());
                    Short offset= pubAxisConfig.getAxisOffset();
                    String postion=pubAxisConfig.getAxisPosition();
                    if(StringUtils.isNotEmpty(postion))
                        yAxis.put("position",pubAxisConfig.getAxisOffset());
                    if(offset!=null && offset>0)
                        yAxis.put("offset",pubAxisConfig.getAxisOffset());

                    YAxisList.add(yAxis);
                }
            }else {
                Map<String,Object> xAxis=new HashMap<>();
                xAxis.put("type","value");
                YAxisList.add(xAxis);
            }


            //坐标显示
            List<String> legend=pubDashboardDataList.stream().map(x->x.getDataName()).collect(Collectors.toList());
            List<Map<String,Object>> series=new ArrayList<>();
            for (PubDashboardData pubDashboardData :pubDashboardDataList) {
                Map<String,Object> serie=new HashMap<>();
                serie.put("name",pubDashboardData.getDataName());
                serie.put("type",pubDashboardData.getChartType());
                serie.put("color",pubDashboardData.getColor());
                List<Object> datas=new ArrayList<>();
                for (String date: xdata) {
                    datas.add(MathUtil.GetevenNum(0,1000));
                }
                serie.put("data",datas);
                series.add(serie);
            }
            reslut.put("title",config.getDashboardTitle());
            reslut.put("legend",legend);
            reslut.put("xAxis",xAxisList);
            reslut.put("yAxis",YAxisList);
            reslut.put("series",series);

        }else{
            throw new ServiceException("501","没有设置数据");
        }
        return  reslut;
    }
}
