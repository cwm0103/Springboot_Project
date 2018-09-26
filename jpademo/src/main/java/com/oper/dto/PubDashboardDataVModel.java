package com.oper.dto;

import com.oper.entity.PubDashboardData;

import java.util.List;

/**
 * cwm
 * add
 * 2018-8-11
 */
public class PubDashboardDataVModel extends PubDashboardData {

    private List<DataDispaly> dataDispalies;

    public List<DataDispaly> getDataDispalies() {
        return dataDispalies;
    }

    public void setDataDispalies(List<DataDispaly> dataDispalies) {
        this.dataDispalies = dataDispalies;
    }

}

