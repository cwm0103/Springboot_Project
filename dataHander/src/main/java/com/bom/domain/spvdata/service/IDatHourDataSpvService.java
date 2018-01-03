package com.bom.domain.spvdata.service;

import com.bom.domain.IService;
import com.bom.domain.spvdata.model.DatHourDataSpv;

import java.util.Date;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IDatHourDataSpvService extends com.bom.domain.IService<DatHourDataSpv> {
    /**
     *插入或修改一个实体数据
     * @param station_id
     * @param code
     * @param date
     * @param cur_value
     */
    public void InsertOrUpdatedataSpv(String station_id, String code, Date date, Double cur_value);

    /**
     * 插入辐照值和即时功率
     * @param station_id
     * @param param_Name
     * @param cur_value
     */
     void InsertOrUpdatedataFuZhaoJSGYSpv(String station_id,String param_Name,Date date,Double cur_value);



}