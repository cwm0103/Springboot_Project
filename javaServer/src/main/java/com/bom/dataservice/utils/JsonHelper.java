package com.bom.dataservice.utils;





import javax.sound.midi.Synthesizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chenwangming on 2017/11/16.
 */
public class JsonHelper {
    /**
     * 遍历json格式数据
     * @param json
     * @return
     */
    public static Object traveseJson(Object json) throws JSONException {

        if(json == null){
            return null;
        }
        if(json instanceof JSONObject){//json 是一个map
            //创建一个json对象
            JSONObject jsonObj = new  JSONObject();
            //将json转换为JsonObject对象
            JSONObject jsonStr = (JSONObject) json;
            //迭代器迭代 map集合所有的keys
            Iterator it = jsonStr.keys();
            while(it.hasNext()){
                //获取map的key
                String key = (String) it.next();
                //得到value的值
                Object value = jsonStr.get(key);
                //System.out.println(value);
                //递归遍历
                jsonObj.put(key, traveseJson(value));

            }
            return jsonObj;

        }else if(json instanceof JSONArray){// if  json 是 数组
            JSONArray jsonAry = new JSONArray();
            JSONArray jsonStr = (JSONArray) json;
            //获取Array 的长度
            int length = jsonStr.length();
            for (int i = 0; i <length; i++) {
                jsonAry.put(traveseJson(jsonStr.get(i)));
            }

            return jsonAry;

        }else {//其他类型

            return json;
        }
    }

    public static List<String> traveseJson(Object json,String filed) throws JSONException {

        if(json == null){
            return null;
        }
        if(json instanceof JSONObject) {//json 是一个map
            //创建一个json对象
            List<String> list = new LinkedList<String>();
            //将json转换为JsonObject对象
            JSONObject jsonStr = (JSONObject) json;
            //迭代器迭代 map集合所有的keys
            Iterator it = jsonStr.keys();
            while (it.hasNext()) {
                //获取map的key
                //String key = (String) it.next();
                //得到value的值
                Object value = jsonStr.get(filed);
                //System.out.println(value);
                //递归遍历
                //jsonObj.put(key, traveseJson(value));
                list.add(value.toString());

            }
            return list;
        }
        return null;
    }
}
