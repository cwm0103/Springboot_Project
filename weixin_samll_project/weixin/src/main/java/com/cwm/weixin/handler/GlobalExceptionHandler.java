package com.cwm.weixin.handler;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Map<String,Object> exceptionHandler(HttpServletRequest req, Exception e)
    {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("ok",false);
        map.put("errMsg",e.getMessage());
        return map;
    }
}
