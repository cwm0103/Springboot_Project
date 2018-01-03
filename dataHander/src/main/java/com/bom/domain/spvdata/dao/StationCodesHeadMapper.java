package com.bom.domain.spvdata.dao;

import com.bom.domain.Mapper;
import com.bom.domain.spvdata.model.StationCodesHead;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface StationCodesHeadMapper extends com.bom.domain.Mapper<StationCodesHead> {

    /**
     * 获取站点参数
     * @param station_Id
     * @return
     */
     List<StationCodesHead> getStationCodeshead(@Param("station_Id") String station_Id);
}