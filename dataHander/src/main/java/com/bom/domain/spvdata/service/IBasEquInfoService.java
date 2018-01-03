package com.bom.domain.spvdata.service;

import com.bom.domain.IService;
import com.bom.domain.spvdata.model.BasEquInfo;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IBasEquInfoService extends com.bom.domain.IService<BasEquInfo> {
    /**
     * 根据 station_id 和 equ_category_big 来获取设备数据
     * @param station_id
     * @param equ_category_big
     * @return
     */
     List<BasEquInfo> getListEquInfo(String station_id, Integer equ_category_big);
}