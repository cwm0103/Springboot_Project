package com.bom.domain.spvdata.service.imp;

import com.bom.domain.BaseService;
import com.bom.domain.spvdata.model.BasEquInfo;
import com.bom.domain.spvdata.service.IBasEquInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class BasEquInfoServiceImp extends BaseService<BasEquInfo> implements IBasEquInfoService {
    @Autowired
    private com.bom.domain.spvdata.dao.BasEquInfoMapper BasEquInfoMapper;

    /**
     * 根据站id和类别id来获取所有设备
     * @param station_id
     * @param equ_category_big
     * @return
     */
    @Override
    public List<BasEquInfo> getListEquInfo(String station_id, Integer equ_category_big) {
        return BasEquInfoMapper.getListEquInfo(station_id,equ_category_big);
    }
}