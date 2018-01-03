package com.func.entity;

import java.util.Date;

/**
 * Created by chenwangming on 2017/8/22.
 */
public class DataReback {
    private Date sd_datatime;
    private float sd_value;
    private String sd_io;
    private Date sd_data;

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

    public String getSd_io() {
        return sd_io;
    }

    public void setSd_io(String sd_io) {
        this.sd_io = sd_io;
    }

    public Date getSd_data() {
        return sd_data;
    }

    public void setSd_data(Date sd_data) {
        this.sd_data = sd_data;
    }
}
