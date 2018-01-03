package com.bom.domain.mix.dao;

import com.bom.domain.Mapper;
import com.bom.domain.mix.model.ResultDay;
import com.bom.domain.mix.model.ResultHour;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface ResultDayMapper extends com.bom.domain.Mapper<ResultDay> {
    List<ResultDay> findByConditon(@Param("projectId")String projectId, @Param("code")String code, @Param("recTime")String recTime ) ;

    /**
     * 插入一条数据 resultDay
     * @param ResultDay
     * @return
     */
    Integer insertResultDay(ResultDay resultDay);

    /**
     * 修改一条数据 resultDay
     * @param ResultDay
     * @return
     */
    Integer updateResultDay(ResultDay resultDay);
}