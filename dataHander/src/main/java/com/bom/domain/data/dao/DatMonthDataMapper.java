package com.bom.domain.data.dao;

import com.bom.domain.Mapper;
import com.bom.domain.data.model.DatMonthData;
import com.bom.domain.mix.model.ResultMonth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface DatMonthDataMapper extends com.bom.domain.Mapper<DatMonthData> {
    DatMonthData findMonthStartData(@Param("projectId") String projectId, @Param("code") String code, @Param("timeStr") String timeStr) ;

    List<DatMonthData> findByConditon(@Param("projectId")String projectId, @Param("code")String code, @Param("timeStr")String recTime ) ;

    BigDecimal calSum(@Param("projectId")String projectId, @Param("code")String code, @Param("beginDate")String begin,@Param("endDate") String end) ;

    /**
     * 获取今日最后一条数据
     * @param station_id
     * @return
     */
    Object selectDatMonthDataLast(@Param("station_id")String station_id);

    /**
     * 插入一条月数据
     * @param datMonthData
     * @return
     */
    Integer insertDataMonthData(DatMonthData datMonthData);
    /**
     * 修改一条月数据
     * @param datMonthData
     * @return
     */
    Integer updateDataMonthData(DatMonthData datMonthData);

    /**
     * 每月总和
     * @param station_id
     * @param beginDate
     * @param endDate
     * @return
     */
    Object selectMonthTotal(@Param("station_id")String station_id,@Param("beginDate")String beginDate,@Param("endDate")String endDate);
}