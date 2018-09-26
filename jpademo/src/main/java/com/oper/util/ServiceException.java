package com.oper.util;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @Auther: jindb
 * @Date: 2018/8/21 11:18
 * @Description:
 */
public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = -4742832112872227456L;
    /**系统错误码*/
    private String code;
    /**错误描述*/
    private String msg;
    /**外部错误码*/
    private String logMsg;

    public ServiceException() {
        super();
    }
    public ServiceException(Throwable t) {
        super(t);
    }

    public ServiceException(String code, String msg) {
        super(code + '|' + msg);
        this.code = code;
        this.msg = msg;
    }
    public ServiceException(String code, String msg, String logMsg) {
        super(code + '|' + msg +  '|' + logMsg);
        this.code = code;
        this.msg = msg;
        this.logMsg = logMsg;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getLogMsg() {
        return logMsg;
    }
    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
