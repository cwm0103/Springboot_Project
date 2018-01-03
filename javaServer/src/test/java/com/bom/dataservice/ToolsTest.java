package com.bom.dataservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bom.dataservice.utils.HTTPUtil;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-21
 * Time: 19:04
 */
public class ToolsTest {
    @Test
    public void TestApi() {
//        String url="http://10.37.147.243:4242/api/query?start=2017/11/21-00:00:00&end=2017/11/25-00:00:00&m=none:1h-max-none:fns.FsInt%7BstaId=1009,equipID=GSB1,equipMK=GSB%7D";
//        String content= HTTPUtil.getContent(url);
//        System.out.println(content);
//        JSONArray jsonArray=JSONObject.parseArray(content);
//
//        JSONObject jsonObject=jsonArray.getJSONObject(0);
//        JSONObject dpsJson=jsonObject.getJSONObject("dps");
//        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        List<Long>  list=new ArrayList<>();
//        List<Map.Entry<String,Object>> maplist = new ArrayList<Map.Entry<String, Object>>((Collection<? extends Map.Entry<String, Object>>) dpsJson.entrySet());
//         Collections.sort(maplist,(o1,o2)->{
//            return o1.getKey().compareTo(o2.getKey());
//        });
//
//        for (Map.Entry<String,Object> sortmap:maplist) {
//            long time=Long.parseLong(sortmap.getKey());
//            Date date= new Date((time*1000));
//           System.out.println(sdf.format(date)+"("+sortmap.getValue() +")");
//        }



    }

}
