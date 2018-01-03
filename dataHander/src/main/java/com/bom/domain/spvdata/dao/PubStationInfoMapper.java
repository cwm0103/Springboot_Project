package com.bom.domain.spvdata.dao;

import com.bom.domain.Mapper;
import com.bom.domain.spvdata.model.PubStationInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface PubStationInfoMapper extends com.bom.domain.Mapper<PubStationInfo> {
    /**
     * 获取所有站点信息
     * @return
     */
    public List<PubStationInfo> getList();
}