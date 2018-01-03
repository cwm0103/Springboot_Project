package com.bom.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by enn on 2017/11/22.
 */
public class DealParamsUtil {

    /**
     * 根据传入的参数拼成所需要的请求json字符串
     * @param beginTime
     * @param endTime
     * @param codes
     * @return
     */
     public static JSONObject dealRequestParams(String beginTime,String endTime,String projectId,String downsample,String codes){
          JSONObject params = new JSONObject();
          params.put("start", beginTime) ;
          params.put("end",endTime) ;

          String[] codeArray = codes.split(",") ;
          JSONArray queries = new JSONArray();
          for(String code : codeArray){
              String[] split = code.split("_") ;
              JSONObject t = new JSONObject();
              t.put("aggregator", "none");
              t.put("metric", "fns." + split[2]);
              t.put("downsample",downsample) ;
              JSONObject tags = new JSONObject();
              tags.put("staId", projectId);
              tags.put("equipID", split[1]);
              tags.put("equipMK", split[0]);

              t.put("tags", tags);
              queries.add(t);
          }
          params.put("queries", queries);
          return  params;//.toJSONString(params) ;
     }

     public static  void dealResponseParams(String result){
         JSONArray bas = JSONArray.parseArray(result);
         Iterator<Object> iterator = bas.iterator();
         String value;
         Date time;
         JSONObject next;
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
         while (iterator.hasNext()) {
             next = (JSONObject) iterator.next();
             String metric = next.getString("metric");

             String fs = metric.split("\\.")[1];
             JSONObject tags = next.getJSONObject("tags");
             String equipID = tags.getString("equipID");
             String equipMK = tags.getString("equipMK");
             //cardCord = equipMK + "_" + equipID + "_" + fs;
             JSONObject dps = next.getJSONObject("dps");
             Set<String> strings = dps.keySet();
             List<String> keyList = new ArrayList(strings) ;
             Collections.sort(keyList);
             for(String key : keyList){
                 System.out.println(sdf.format(new Date(Long.valueOf(key)*1000L))+"  "+dps.getBigDecimal(key)) ;
             }
             System.out.println();
//             time = new Date(Long.valueOf(max));
//             //  cardCord = next.getString("name");
//             value = dps.getIntValue(max) + "";
         }
     }

     public static void main(String[] args){
//          JSONArray jsonObject = HttpClientHelper.httpPostArray("http://10.37.147.243:4242/api/query",
//         dealRequestParams("1509465600000","","1009","1n-last-none","GSB_GSB1_Fs") );
//         dealResponseParams(jsonObject.toJSONString());
//         dealResponseParams("") ;

     }
}
