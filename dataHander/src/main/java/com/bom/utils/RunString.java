package com.bom.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Map;

/**
 * <p>Title: RunString</p>
 * <p>Description: 字符串的数值运算</p> 
 * 20171116 create
 * @version 1.0
 */
public class RunString {
    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");  
    
    public Double calString(String fun, Map<String,String> params) throws Exception{
        for ( String key : params.keySet()) {
            fun = fun.replaceAll(key, params.get(key));
        }
        System.out.println("\t计算公式是：" + fun);
        return Double.valueOf(jse.eval(fun).toString());
    }
}
