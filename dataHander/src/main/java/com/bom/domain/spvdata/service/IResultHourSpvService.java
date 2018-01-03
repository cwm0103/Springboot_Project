package com.bom.domain.spvdata.service;

import com.bom.domain.IService;
import com.bom.domain.spvdata.model.ResultHourSpv;
import org.apache.ibatis.annotations.Param;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IResultHourSpvService extends com.bom.domain.IService<ResultHourSpv> {

    /**
     * 插入一条结果数据
     * @param resultHourSpv
     * @return
     */
     int insertResultHourSpv(ResultHourSpv resultHourSpv);

    /**
     * 修改一条结果数据
     * @param resultHourSpv
     * @return
     */
     int updateResultHourSpv(ResultHourSpv resultHourSpv);
}