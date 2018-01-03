package com.bom.domain.data.service;

import com.bom.domain.IService;
import com.bom.domain.data.model.StationTop;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IStationTopService extends com.bom.domain.IService<StationTop> {

    int save(StationTop entity);

   StationTop getStationDataBySiteIdAndType(String station_id,int type);
}