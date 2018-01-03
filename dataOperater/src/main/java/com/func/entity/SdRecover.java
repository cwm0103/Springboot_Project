package com.func.entity;

import java.util.Date;

/**
 * 山东数据恢复
 * Created by chenwangming on 2017/8/16.
 */
public class SdRecover {

    private String  sd_code;

    private String sd_io;

    private Date sd_datatime;

    private float sd_value;

    public String getSd_code() {
        return sd_code;
    }

    public void setSd_code(String sd_code) {
        this.sd_code = sd_code;
    }

    public String getSd_io() {
        return sd_io;
    }

    public void setSd_io(String sd_io) {
        this.sd_io = sd_io;
    }

    public Date getSd_datatime() {
        return sd_datatime;
    }

    public void setSd_datatime(Date sd_datatime) {
        this.sd_datatime = sd_datatime;
    }

    public float getSd_value() {
        return sd_value;
    }

    public void setSd_value(float sd_value) {
        this.sd_value = sd_value;
    }
}
