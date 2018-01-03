package com.bom.domain.data.model;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-24
 * Time: 10:58
 */
public class TSDBResult {
    private String code;
    private String date;
    private BigDecimal value;
    private String staId;
    private String datavalue;

    public TSDBResult() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getStaid() {
        return staId;
    }

    public void setStaid(String staid) {
        this.staId = staid;
    }

    public String getDatavalue() {
        return datavalue;
    }

    public void setDatavalue(String datavalue) {
        this.datavalue = datavalue;
    }
}
