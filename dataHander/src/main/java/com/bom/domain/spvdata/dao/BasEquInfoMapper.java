package com.bom.domain.spvdata.dao;

import com.bom.domain.Mapper;
import com.bom.domain.spvdata.model.BasEquInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface BasEquInfoMapper extends com.bom.domain.Mapper<BasEquInfo> {
    /**
     * 根据station_id 和类别来获取设备
     * @param station_id
     * @param equ_category_big
     * @return
     */
     List<BasEquInfo> getListEquInfo(@Param("station_id") String station_id,@Param("equ_category_big") Integer equ_category_big);

}