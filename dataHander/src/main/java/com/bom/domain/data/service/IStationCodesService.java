package com.bom.domain.data.service;

import com.bom.domain.IService;
import com.bom.domain.data.model.StationCodes;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IStationCodesService extends com.bom.domain.IService<StationCodes> {

    List<StationCodes> selectAll();
    /**
     * 获取所有的站点code
     * @param station_Id
     * @param param_Name
     * @return
     */
    List<StationCodes> getStationCodes(String station_Id, String param_Name);
}