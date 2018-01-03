package com.bom.domain.data.dao;

import com.bom.domain.Mapper;
import com.bom.domain.data.model.DatDayData;
import com.bom.domain.data.model.DatHourData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface DatDayDataMapper extends com.bom.domain.Mapper<DatDayData> {
    void  insertDayDataBatch(@Param("datDayDataList")List<DatDayData> datDayDataList);

    List< DatDayData> GetDayData(@Param("projectId")String projectId,@Param("code")String code,@Param("colTime")String colTime);

    BigDecimal calSum(@Param("projectId")String projectId, @Param("code")String code, @Param("beginDate")String begin, @Param("endDate") String end) ;

    /**
     * 根据站id来查询最后一个时间数据
     * @param station_id
     * @return
     */
    Object selectdaydataLast(@Param("station_id") String station_id);

    /**
     * 插入一条日数据
     * @param datDayData
     * @return
     */
    Integer insertDatDayData(DatDayData datDayData);

    /**
     * 修改一条日数据
     * @param datDayData
     * @return
     */
    Integer updateDatDayData(DatDayData datDayData);

    /**
     * 去每日总和
     * @param station_id
     * @param beginDate
     * @param endDate
     * @return
     */
    Object selectdayTotal(@Param("station_id")String station_id,@Param("beginDate")String beginDate,@Param("endDate")String endDate);


}