package com.bom.domain.data.service;

import com.bom.domain.IService;
import com.bom.domain.data.model.DatMonthData;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IDatMonthDataService extends com.bom.domain.IService<DatMonthData> {

    void handlerMonth() ;

    /**
     * 获取月组装数据
     * @param station_id
     * @param code
     * @param beginDate
     * @param endDate
     */
    void InsertOrUpdatedataSpv(String station_id, String code, String  beginDate, String  endDate);
}