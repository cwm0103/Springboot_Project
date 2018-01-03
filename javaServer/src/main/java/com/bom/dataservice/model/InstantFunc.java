package com.bom.dataservice.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-25
 * Time: 16:10
 */
public class InstantFunc {

    private int id;
    private String staid;
    private String cal_code;
    private String cal_code_name;
    private String list_var;
    private String function;
    private String  func_desc;
    private Date    create_date;

    public InstantFunc() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaid() {
        return staid;
    }

    public void setStaid(String staid) {
        this.staid = staid;
    }

    public String getCal_code() {
        return cal_code;
    }

    public void setCal_code(String cal_code) {
        this.cal_code = cal_code;
    }

    public String getCal_code_name() {
        return cal_code_name;
    }

    public void setCal_code_name(String cal_code_name) {
        this.cal_code_name = cal_code_name;
    }

    public String getList_var() {
        return list_var;
    }

    public void setList_var(String list_var) {
        this.list_var = list_var;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunc_desc() {
        return func_desc;
    }

    public void setFunc_desc(String func_desc) {
        this.func_desc = func_desc;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
