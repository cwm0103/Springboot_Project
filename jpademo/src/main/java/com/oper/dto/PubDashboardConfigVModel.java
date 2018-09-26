package com.oper.dto;

import com.oper.entity.PubDashboardConfig;
import com.oper.entity.PubDashboardData;
import com.oper.entity.PubView;

import java.util.List;

public class PubDashboardConfigVModel extends PubDashboardConfig {



//    List<PubDashboardData> pubDashboardData;
//
//    public List<PubDashboardData> getPubDashboardData() {
//        return pubDashboardData;
//    }
//
//    public void setPubDashboardData(List<PubDashboardData> pubDashboardData) {
//        this.pubDashboardData = pubDashboardData;
//    }

    public List<PubDashboardDataVModel> getPubDashboardDataVModels() {
        return pubDashboardDataVModels;
    }

    public void setPubDashboardDataVModels(List<PubDashboardDataVModel> pubDashboardDataVModels) {
        this.pubDashboardDataVModels = pubDashboardDataVModels;
    }

    private List<PubDashboardDataVModel> pubDashboardDataVModels;



}
