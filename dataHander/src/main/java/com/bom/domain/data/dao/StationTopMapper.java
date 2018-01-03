package com.bom.domain.data.dao;

import com.bom.domain.Mapper;
import com.bom.domain.data.model.StationTop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Savepoint;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface StationTopMapper extends com.bom.domain.Mapper<StationTop> {

    int save(@Param("entity") StationTop entity);

    StationTop getStationDataBySiteIdAndType(@Param("station_id")String station_id,@Param("type")int type);
}