package com.oper.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Auther: jindb
 * @Date: 2018/9/15 11:12
 * @Description:
 */
@Data
public class DashboardDataDTO implements Serializable  {
    private Integer dataId;
    private String instanceName;
    private  String dataName;
    private String unit;
    /**
     * 1:瞬时量;2:累计量
     */
    private Integer dataType;

    public DashboardDataDTO(Integer dataId, String instanceName, String dataName, String unit,Integer dataType) {
        this.dataId = dataId;
        this.instanceName = instanceName;
        this.dataName = dataName;
        this.unit = unit;
        this.dataType = dataType;
    }
}
