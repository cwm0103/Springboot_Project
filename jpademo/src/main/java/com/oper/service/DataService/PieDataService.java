package com.oper.service.DataService;

import com.alibaba.fastjson.JSONArray;
import com.oper.entity.PubDashboardConfig;
import com.oper.entity.PubDashboardData;
import com.oper.entity.vo.DataParmerVO;
import com.oper.service.PubDashboardDataService;
import com.oper.util.DataUtil;
import com.oper.util.MathUtil;
import com.oper.util.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: jindb
 * @Date: 2018/8/21 15:56
 * @Description:
 */
@Component("pie")
@Slf4j
public class PieDataService implements IDataService {
    @Autowired
    private PubDashboardDataService pubDashboardDataService;

    @Override
    public Map<String, Object> getData(PubDashboardConfig config, List<PubDashboardData> pubDashboardDataList, JSONArray jsonObject, DataParmerVO prame) throws Exception {
        if(null==jsonObject)
        {
            return getData(config,pubDashboardDataList,jsonObject,prame,true);
        }else{
            return getData(config,pubDashboardDataList,jsonObject,prame,false);
        }
    }

    private Map<String, Object> getData(PubDashboardConfig config,  List<PubDashboardData> pubDashboardDataList, JSONArray jsonObject, DataParmerVO prame,boolean isTest){
        Map<String,Object> reslut=new HashMap<>();
       // List<PubDashboardData> pubDashboardDataList= pubDashboardDataService.getListByDid(prame.getDashboardId());



        List<String> colors=new ArrayList<>();

        Map<String,Object> mapResult=null;
        if(pubDashboardDataList!=null && pubDashboardDataList.size()>0){
            String valueType=pubDashboardDataList.get(0).getDataType().equals(1)?"avg":"sum";//config.getValueType();
            if(jsonObject!=null && valueType.equals("sum")){
                mapResult= DataUtil.paseDataSum(jsonObject);
            }
            if(jsonObject!=null && valueType.equals("avg")){
                mapResult= DataUtil.paseDataAvg(jsonObject);
            }

            List<String> legend=new ArrayList<>();
            List<Map<String,Object>> datas=new ArrayList<>();
            for (PubDashboardData pubDashboardData: pubDashboardDataList) {
                Map<String,Object> data=new HashMap<>();
                data.put("name",pubDashboardData.getDataName());
                if(isTest)
                {
                    data.put("value",MathUtil.GetevenNum(0,1000));
                }else{
                    data.put("value",mapResult.get(pubDashboardData.getCode()));
                }
                datas.add(data);
                colors.add(pubDashboardData.getColor());
                legend.add(pubDashboardData.getDataName());
            }
            List<Map<String,Object>> series=new ArrayList<>();
            Map<String,Object> serie=new HashMap<>();
            serie.put("name",config.getDashboardTitle());
            serie.put("type","pie");
            serie.put("data",datas);
            series.add(serie);
            reslut.put("series",series);
            reslut.put("legend",legend);
            reslut.put("color",colors);
        }else{
            throw new ServiceException("501","没有设置数据");
        }

        return reslut;
    }

    private Map<String, Object> getTestData(PubDashboardConfig config, DataParmerVO prame){
        Map<String,Object> reslut=new HashMap<>();
        List<PubDashboardData> pubDashboardDataList= pubDashboardDataService.getListByDid(prame.getDashboardId());
        List<String> colors=new ArrayList<>();
        if(pubDashboardDataList!=null && pubDashboardDataList.size()>0){
            List<String> legend=new ArrayList<>();
            List<Map<String,Object>> datas=new ArrayList<>();
            for (PubDashboardData pubDashboardData: pubDashboardDataList) {
                Map<String,Object> data=new HashMap<>();
                data.put("name",pubDashboardData.getDataName());
                data.put("value",MathUtil.GetevenNum(0,1000));
                datas.add(data);
                colors.add(pubDashboardData.getColor());
                legend.add(pubDashboardData.getDataName());
            }
            List<Map<String,Object>> series=new ArrayList<>();
            Map<String,Object> serie=new HashMap<>();
            serie.put("name",config.getDashboardTitle());
            serie.put("type","pie");
            serie.put("data",datas);
            series.add(serie);
            reslut.put("series",series);
            reslut.put("legend",legend);
            reslut.put("color",colors);
        }else{
            throw new ServiceException("501","没有设置数据");
        }
        return reslut;
    }
}
