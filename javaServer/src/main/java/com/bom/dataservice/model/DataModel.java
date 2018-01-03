package com.bom.dataservice.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-11
 * Time: 13:30
 */
public class DataModel implements Serializable {

    private static final long serialVersionUID = 5406587404283665647L;

    public DataModel() {
    }

    String code;
    double value;
    String col_time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCol_time() {
        return col_time;
    }

    public void setCol_time(String col_time) {
        this.col_time = col_time;
    }
}
