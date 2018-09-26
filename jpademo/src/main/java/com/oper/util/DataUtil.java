package com.oper.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: jindb
 * @Date: 2018/9/12 10:19
 * @Description:
 */
public class DataUtil {

    /**
     * 解析数据格式
     * @param jsonObject
     * @return
     */
    public static Map<String,List<Object>> paseData(JSONArray jsonObject){
       // String json="[{\"code\": \"CA06ES01_H2\",\"datas\": [{\"col_time\": \"2018-05-10 11:00:00\",\"curvalue\": \"\",\"rawvalue\": \"\"},{\"col_time\": \"2018-05-10 11:01:00\",\"curvalue\": \"\",\"rawvalue\": \"\"},{ \"col_time\": \"2018-05-10 11:02:00\",\"curvalue\": \"\",\"rawvalue\": \"\"}]}]";
        Map<String,List<Object>> result=new HashMap<>();
        //jsonObject=JSONArray.parseArray(json);
        for (int i=0;i<jsonObject.size();i++){
            JSONObject codedatas = jsonObject.getJSONObject(i);
            String code=codedatas.getString("code");
            JSONArray dataJson=  codedatas.getJSONArray("datas");
            List<Object> datas=new LinkedList<>();
            for (int j=0;j<dataJson.size();j++) {
                JSONObject jo=dataJson.getJSONObject(j);
                String data=jo.getString("rawvalue");
                datas.add(data);
            }
            result.put(code,datas);
        }
        return  result;
    }

    /**
     * 数据求和
     * @param jsonObject
     * @return
     */
    public static Map<String,Object> paseDataSum(JSONArray jsonObject){
        //String json="[{\"code\": \"CA06ES01_H2\",\"datas\": [{\"col_time\": \"2018-05-10 11:00:00\",\"curvalue\": \"\",\"rawvalue\": \"\"},{\"col_time\": \"2018-05-10 11:01:00\",\"curvalue\": \"\",\"rawvalue\": \"\"},{ \"col_time\": \"2018-05-10 11:02:00\",\"curvalue\": \"\",\"rawvalue\": \"\"}]}]";
        Map<String,Object> result=new HashMap<>();
        //jsonObject=JSONArray.parseArray(json);
        for (int i=0;i<jsonObject.size();i++){
            JSONObject codedatas = jsonObject.getJSONObject(i);
            String code=codedatas.getString("code");
            JSONArray dataJson=  codedatas.getJSONArray("datas");
            Double sum=0.0;
            for (int j=0;j<dataJson.size();j++) {
                JSONObject jo=dataJson.getJSONObject(j);
                String data=jo.getString("rawvalue");
                if(StringUtils.isNoneBlank(data))
                {
                    sum+=Double.parseDouble(data);
                }
            }
            result.put(code,sum);
        }
        return  result;
    }

    /**
     * 求平均
     * @param jsonObject
     * @return
     */
    public static Map<String,Object> paseDataAvg(JSONArray jsonObject){
      //  String json="[{\"code\": \"CA06ES01_H2\",\"datas\": [{\"col_time\": \"2018-05-10 11:00:00\",\"curvalue\": \"\",\"rawvalue\": \"\"},{\"col_time\": \"2018-05-10 11:01:00\",\"curvalue\": \"\",\"rawvalue\": \"\"},{ \"col_time\": \"2018-05-10 11:02:00\",\"curvalue\": \"\",\"rawvalue\": \"\"}]}]";
        Map<String,Object> result=new HashMap<>();
      //  jsonObject=JSONArray.parseArray(json);
        for (int i=0;i<jsonObject.size();i++){
            JSONObject codedatas = jsonObject.getJSONObject(i);
            String code=codedatas.getString("code");
            JSONArray dataJson=  codedatas.getJSONArray("datas");
            Double sum=0.0;
            int count=0;
            for (int j=0;j<dataJson.size();j++) {
                JSONObject jo=dataJson.getJSONObject(j);
                String data=jo.getString("rawvalue");
                if(StringUtils.isNoneBlank(data))
                {
                    Double ddata= Double.parseDouble(data);
                    if(ddata!=0){
                        count++;
                    }
                    sum+=Double.parseDouble(data);
                }
            }
            DecimalFormat df = new DecimalFormat("#.00");
            result.put(code,df.format(sum/count));
        }
        return  result;
    }




}
