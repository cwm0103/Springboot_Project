package com.bom.dataservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by chenwangming on 2017/11/24.
 */
@Entity
public class Equparamhbase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;
    private String  Station_Id;
    private  Integer EquipmentID;
    private Integer EquipmentType;
    private String ParamName;
    private String ParamName_EN;
    private String ParamFieldName_HBase;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getStation_Id() {
        return Station_Id;
    }

    public void setStation_Id(String station_Id) {
        Station_Id = station_Id;
    }

    public Integer getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(Integer equipmentID) {
        EquipmentID = equipmentID;
    }

    public Integer getEquipmentType() {
        return EquipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        EquipmentType = equipmentType;
    }

    public String getParamName() {
        return ParamName;
    }

    public void setParamName(String paramName) {
        ParamName = paramName;
    }

    public String getParamName_EN() {
        return ParamName_EN;
    }

    public void setParamName_EN(String paramName_EN) {
        ParamName_EN = paramName_EN;
    }

    public String getParamFieldName_HBase() {
        return ParamFieldName_HBase;
    }

    public void setParamFieldName_HBase(String paramFieldName_HBase) {
        ParamFieldName_HBase = paramFieldName_HBase;
    }
}
