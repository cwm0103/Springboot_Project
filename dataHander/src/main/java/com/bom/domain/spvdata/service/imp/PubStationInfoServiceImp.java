package com.bom.domain.spvdata.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.spvdata.model.PubStationInfo;
import com.bom.domain.spvdata.service.IPubStationInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class PubStationInfoServiceImp extends BaseService<PubStationInfo> implements IPubStationInfoService {
    @Autowired
    private com.bom.domain.spvdata.dao.PubStationInfoMapper PubStationInfoMapper;

    @Override
    @TargetDataSource(name="datadb")
    public List<PubStationInfo> getList() {
        return PubStationInfoMapper.getList();
    }
}