package com.bom.domain.spvdata.dao;

import com.bom.domain.Mapper;
import com.bom.domain.spvdata.model.ResultHourSpv;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface ResultHourSpvMapper extends com.bom.domain.Mapper<ResultHourSpv> {
    /**
     * 插入一条数据 resultHourSpv
     * @param resultHourSpv
     * @return
     */
     Integer insertResultHour(ResultHourSpv resultHourSpv);

    /**
     * 修改一条数据 resultHourSpv
     * @param resultHourSpv
     * @return
     */
     Integer updateResultHour(ResultHourSpv resultHourSpv);
}