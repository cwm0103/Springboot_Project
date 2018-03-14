package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import java.sql.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenwangming123
 * @since 2018-03-14
 */
@TableName("tbl_house")
public class TblHouse extends Model<TblHouse> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @TableField("hour_user")
    private String hourUser;
    @TableField("hour_address")
    private String hourAddress;
    @TableField("hour_data")
    private Date hourData;
    @TableField("hour_desc")
    private String hourDesc;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHourUser() {
        return hourUser;
    }

    public void setHourUser(String hourUser) {
        this.hourUser = hourUser;
    }

    public String getHourAddress() {
        return hourAddress;
    }

    public void setHourAddress(String hourAddress) {
        this.hourAddress = hourAddress;
    }

    public Date getHourData() {
        return hourData;
    }

    public void setHourData(Date hourData) {
        this.hourData = hourData;
    }

    public String getHourDesc() {
        return hourDesc;
    }

    public void setHourDesc(String hourDesc) {
        this.hourDesc = hourDesc;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TblHouse{" +
        "id=" + id +
        ", hourUser=" + hourUser +
        ", hourAddress=" + hourAddress +
        ", hourData=" + hourData +
        ", hourDesc=" + hourDesc +
        "}";
    }
}
