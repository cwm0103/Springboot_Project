package com.bom.domain.spvdata.service;

import com.bom.domain.IService;
import com.bom.domain.spvdata.model.PubStationInfo;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IPubStationInfoService extends com.bom.domain.IService<PubStationInfo> {
    /**
     * 获取所有站点信息
     * @return
     */
     List<PubStationInfo> getList();
}