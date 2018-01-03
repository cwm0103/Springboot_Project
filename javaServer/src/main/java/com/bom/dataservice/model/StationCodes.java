package com.bom.dataservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by chenwangming on 2017/11/27.
 */

@Entity
public class StationCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer id;
    private String project_id;
    private String code;
    private String code_des;
    private Date create_date;
    private String param_name;
    private String param_name_des;

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

    public String getCode_des() {
        return code_des;
    }

    public void setCode_des(String code_des) {
        this.code_des = code_des;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public String getParam_name_des() {
        return param_name_des;
    }

    public void setParam_name_des(String param_name_des) {
        this.param_name_des = param_name_des;
    }
}
