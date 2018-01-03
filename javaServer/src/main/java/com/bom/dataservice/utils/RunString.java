package com.bom.dataservice.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-25
 * Time: 15:32
 */
public class RunString {
    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    public static Double calString(String fun, Map<String,String> params) throws Exception{
        for ( String key : params.keySet()) {
            fun = fun.replaceAll(key, params.get(key));
        }
        System.out.println("\t计算公式是：" + fun);
        return Double.valueOf(jse.eval(fun).toString());
    }
}
