package com.bom.domain.data.service;

import com.bom.domain.IService;
import com.bom.domain.data.model.DatYearData;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IDatYearDataService extends com.bom.domain.IService<DatYearData> {
    void  handlerYear() ;
    /**
     * 根拒時間來判斷當前時間是否是插入還是修改當前電量值
     * @param station_id
     * @param code
     * @param date
     */
     void InsertOrUpdateHourdataSpv(String station_id, String code, String  beginDate, String  endDate);
}