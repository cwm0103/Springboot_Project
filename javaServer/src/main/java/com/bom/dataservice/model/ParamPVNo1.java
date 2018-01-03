package com.bom.dataservice.model;

import java.util.List;

/**
 * Created by chenwangming on 2017/11/16.
 */
public class ParamPVNo1 {
    private String startTime;
    private String endTime;
    private String metricList;
    private List<String> deviceNoList;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMetricList() {
        return metricList;
    }

    public void setMetricList(String metricList) {
        this.metricList = metricList;
    }

    public List<String> getDeviceNoList() {
        return deviceNoList;
    }

    public void setDeviceNoList(List<String> deviceNoList) {
        this.deviceNoList = deviceNoList;
    }
}
