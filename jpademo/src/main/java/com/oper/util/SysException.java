package com.oper.util;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @Auther: jindb
 * @Date: 2018/8/21 11:22
 * @Description:
 */
public class SysException extends Exception {
    private static final long serialVersionUID = -4742832112872227456L;
    /**系统错误码*/
    private String code;
    /**错误描述*/
    private String msg;

    public SysException() {
        super();
    }
    public SysException(Throwable t) {
        super(t);
    }
    public SysException(String code, String msg) {
        super(code + '|' + msg);
        this.code = code;
        this.msg = msg;
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
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
