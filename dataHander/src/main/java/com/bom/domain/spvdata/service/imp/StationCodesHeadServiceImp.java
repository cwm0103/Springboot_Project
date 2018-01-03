package com.bom.domain.spvdata.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.spvdata.model.StationCodesHead;
import com.bom.domain.spvdata.service.IStationCodesHeadService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class StationCodesHeadServiceImp extends BaseService<StationCodesHead> implements IStationCodesHeadService {
    @Autowired
    private com.bom.domain.spvdata.dao.StationCodesHeadMapper StationCodesHeadMapper;

    @Override
    @TargetDataSource(name="datadb")
    public List<StationCodesHead> getStationCodeshead(String station_Id) {
        return StationCodesHeadMapper.getStationCodeshead(station_Id);
    }
}