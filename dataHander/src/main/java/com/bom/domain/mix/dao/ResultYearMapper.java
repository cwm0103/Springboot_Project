package com.bom.domain.mix.dao;

import com.bom.domain.Mapper;
import com.bom.domain.mix.model.ResultHour;
import com.bom.domain.mix.model.ResultYear;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface ResultYearMapper extends com.bom.domain.Mapper<ResultYear> {
    List<ResultYear> findByConditon(@Param("projectId")String projectId, @Param("code")String code, @Param("recTime")String recTime ) ;

    Integer insertResultYear(ResultYear resultYear);

    Integer updateResultYear(ResultYear resultYear);
}