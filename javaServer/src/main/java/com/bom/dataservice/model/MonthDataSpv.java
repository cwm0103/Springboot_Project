package com.bom.dataservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by chenwangming on 2017/11/18.
 */
@Entity
public class MonthDataSpv {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String  project_id;
    private String code;
    private double cur_value;
    private Date col_time;
    private Date sys_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getCur_value() {
        return cur_value;
    }

    public void setCur_value(double cur_value) {
        this.cur_value = cur_value;
    }

    public Date getCol_time() {
        return col_time;
    }

    public void setCol_time(Date col_time) {
        this.col_time = col_time;
    }

    public Date getSys_time() {
        return sys_time;
    }

    public void setSys_time(Date sys_time) {
        this.sys_time = sys_time;
    }
}
