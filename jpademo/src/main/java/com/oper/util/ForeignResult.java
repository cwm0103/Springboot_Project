package com.oper.util;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.task.TaskDecorator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ForeignResult {
    public static JSONObject SUCCESS(){
    	Map<String, Object> params=new HashMap<String, Object>();
    	params.put("status", ErrEnumConfig.success.name());
    	params.put("msg", ErrEnumConfig.success.getMsg());
		params.put("data","");
		Consumer<String> d=System.out::println;

		d.accept(params.toString());
    	return JSONObject.parseObject(JSON.toJSONString(params));
    }
    public static  JSONObject SUCCESS(Object info){
    	Map<String, Object> params=new HashMap<String, Object>();
    	params.put("status", ErrEnumConfig.success.name());
    	params.put("msg", ErrEnumConfig.success.getMsg());
    	params.put("data", info);
		Consumer<String> d=System.out::println;

		d.accept(params.toString());
		return JSONObject.parseObject(JSON.toJSONString(params));
    }
    public static  JSONObject FAIL(String status,String msg){
    	Map<String, Object> params=new HashMap<String, Object>();
    	params.put("status", status);
    	params.put("msg", msg);
    	params.put("data","");
		Consumer<String> d=System.out::println;

		d.accept(params.toString());
		return JSONObject.parseObject(JSON.toJSONString(params));
    }

	public static  JSONObject FAIL(){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("status", ErrEnumConfig.fail.name());
		params.put("msg", ErrEnumConfig.fail.getMsg());
		params.put("data","");
		Consumer<String> d=System.out::println;

		d.accept(params.toString());
		return JSONObject.parseObject(JSON.toJSONString(params));
	}

}
