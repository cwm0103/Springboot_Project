package com.bom.domain.data.dao;

import com.bom.domain.Mapper;
import com.bom.domain.data.model.DatMonthData;
import com.bom.domain.data.model.DatYearData;
import com.bom.domain.mix.model.ResultYear;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface DatYearDataMapper extends com.bom.domain.Mapper<DatYearData> {
    DatYearData findYearStartData(@Param("projectId") String projectId, @Param("code") String code, @Param("timeStr") String timeStr) ;
    List<DatYearData> findByConditon(@Param("projectId")String projectId, @Param("code")String code, @Param("timeStr")String timeStr ) ;

    /**
     * 获取今日最后一条数据
     * @param station_id
     * @return
     */
    Object selectDatYearDataLast(@Param("station_id")String station_id);

    /**
     * 插入一条年数据
     * @param datYearData
     * @return
     */
    Integer insertDataYearData(DatYearData datYearData);
    /**
     * 修改一条年数据
     * @param datYearData
     * @return
     */
    Integer updateDataYearData(DatYearData datYearData);
}