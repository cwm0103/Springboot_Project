package com.bom.domain.data.dao;

import com.bom.domain.Mapper;
import com.bom.domain.data.model.StationCodes;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface StationCodesMapper extends com.bom.domain.Mapper<StationCodes> {
    List<StationCodes> selectAll();
    /**
     * 获取所有的code
     * @param station_Id
     * @param param_Name
     * @return
     */
    List<StationCodes> getStationCodes(@Param("station_Id") String station_Id, @Param("param_Name") String param_Name);
}