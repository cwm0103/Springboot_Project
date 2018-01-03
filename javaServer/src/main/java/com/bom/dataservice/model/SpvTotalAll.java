package com.bom.dataservice.model;

import javax.persistence.Entity;

/**
 * Created by chenwangming on 2017/12/5.
 */

public class SpvTotalAll {
    private String station_id;
    private  String yestodayIncome;
    private  String lastmonthIncome;
    private String totalIncome;
    private String totalPower;
    private String totalCO2;
    private String totalCoal;

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getYestodayIncome() {
        return yestodayIncome;
    }

    public void setYestodayIncome(String yestodayIncome) {
        this.yestodayIncome = yestodayIncome;
    }

    public String getLastmonthIncome() {
        return lastmonthIncome;
    }

    public void setLastmonthIncome(String lastmonthIncome) {
        this.lastmonthIncome = lastmonthIncome;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(String totalPower) {
        this.totalPower = totalPower;
    }

    public String getTotalCO2() {
        return totalCO2;
    }

    public void setTotalCO2(String totalCO2) {
        this.totalCO2 = totalCO2;
    }

    public String getTotalCoal() {
        return totalCoal;
    }

    public void setTotalCoal(String totalCoal) {
        this.totalCoal = totalCoal;
    }
}
