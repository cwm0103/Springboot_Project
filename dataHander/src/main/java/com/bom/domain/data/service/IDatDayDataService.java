package com.bom.domain.data.service;

import com.bom.domain.IService;
import com.bom.domain.data.model.DatDayData;
import com.bom.domain.data.model.OptDataCol;
import org.apache.ibatis.annotations.Insert;

import java.awt.*;
import java.util.Date;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IDatDayDataService extends com.bom.domain.IService<DatDayData> {

    void  insertDayDataBatch(List<DatDayData> datDayDataList);
    int updateByPrimaryKey(DatDayData datDayData);

   List<DatDayData> GetDayData(String projectId,String code,String colTime);
    void createDayData();

    /**
     * 插入日数据
     * @param station_id
     * @param code
     * @param date
     * @param lastValue
     */
     void InsertOrUpdatedataSpv(String station_id, String code, Date date, double lastValue);
}