package com.bom.domain.spvdata.service;

import com.bom.domain.IService;
import com.bom.domain.spvdata.model.StationCodesHead;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IStationCodesHeadService extends com.bom.domain.IService<StationCodesHead> {
    /**
     *  //通过Station_Id来获取station_codes_head
     * @param station_Id
     * @return
     */
     List<StationCodesHead> getStationCodeshead(String station_Id);

}