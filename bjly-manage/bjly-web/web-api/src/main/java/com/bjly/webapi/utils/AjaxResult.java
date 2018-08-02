package com.bjly.webapi.utils;

/**
 * cwm
 * 2018-8-2
 * 返回类工具类
 */
public  class AjaxResult {

    private static String code="ok";
    //消息
    private  static String msg="success";
    //数据
    private  static Object data=null;
    /**
     * 成功
     * @return
     */
    public static Object success()
    {
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 成功带参数
     * @param data
     * @return
     */
    public static  Object success(Object data)
    {
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     * @param err
     * @return
     */
    public static Object error(String err)
    {
        Result result=new Result();
        result.setCode("no");
        result.setMsg(err);
        result.setData(data);
        return result;
    }
}

class  Result{


    private String code;
    private String msg;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}