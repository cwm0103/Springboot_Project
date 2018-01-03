package com.bom.domain.mix.dao;

import com.bom.domain.Mapper;
import com.bom.domain.mix.model.ResultHour;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface ResultHourMapper extends com.bom.domain.Mapper<ResultHour> {
    List<ResultHour> findByConditon(@Param("projectId")String projectId,@Param("code")String code,@Param("recTime")String recTime ) ;
}