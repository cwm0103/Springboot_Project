package com.bom.dataservice.model;

import io.swagger.models.auth.In;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by chenwangming on 2017/11/16.
 */
@Entity
public class EquInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer equ_id;
    private String station_id;
    private String  equ_name;
    private String  equ_no;
    private String  specification;
    private String  company_code;
    private String  project_code;
    private String  kks_code;
    private Integer equ_category_big;
    private Integer equ_category_small;
    private Date manufacture_date;
    private Integer amount;
    private String unit;
    private String protect_level;
    private Integer belongsystem;
    private Integer sp_id;
    private String files_id;
    private Integer gateway_id;
    private String device_no;

    public Integer getEqu_id() {
        return equ_id;
    }

    public void setEqu_id(Integer equ_id) {
        this.equ_id = equ_id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getEqu_name() {
        return equ_name;
    }

    public void setEqu_name(String equ_name) {
        this.equ_name = equ_name;
    }

    public String getEqu_no() {
        return equ_no;
    }

    public void setEqu_no(String equ_no) {
        this.equ_no = equ_no;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getKks_code() {
        return kks_code;
    }

    public void setKks_code(String kks_code) {
        this.kks_code = kks_code;
    }

    public Integer getEqu_category_big() {
        return equ_category_big;
    }

    public void setEqu_category_big(Integer equ_category_big) {
        this.equ_category_big = equ_category_big;
    }

    public Integer getEqu_category_small() {
        return equ_category_small;
    }

    public void setEqu_category_small(Integer equ_category_small) {
        this.equ_category_small = equ_category_small;
    }

    public Date getManufacture_date() {
        return manufacture_date;
    }

    public void setManufacture_date(Date manufacture_date) {
        this.manufacture_date = manufacture_date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProtect_level() {
        return protect_level;
    }

    public void setProtect_level(String protect_level) {
        this.protect_level = protect_level;
    }

    public Integer getBelongsystem() {
        return belongsystem;
    }

    public void setBelongsystem(Integer belongsystem) {
        this.belongsystem = belongsystem;
    }

    public Integer getSp_id() {
        return sp_id;
    }

    public void setSp_id(Integer sp_id) {
        this.sp_id = sp_id;
    }

    public String getFiles_id() {
        return files_id;
    }

    public void setFiles_id(String files_id) {
        this.files_id = files_id;
    }

    public Integer getGateway_id() {
        return gateway_id;
    }

    public void setGateway_id(Integer gateway_id) {
        this.gateway_id = gateway_id;
    }

    public String getDevice_no() {
        return device_no;
    }

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }
}
