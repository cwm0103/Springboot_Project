package com.bom.domain.data.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.model.StationCodes;
import com.bom.domain.data.service.IStationCodesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class StationCodesServiceImp extends BaseService<StationCodes> implements IStationCodesService {
    @Autowired
    private com.bom.domain.data.dao.StationCodesMapper StationCodesMapper;

    @Override
    @TargetDataSource(name="datadb")
    public List<StationCodes> selectAll()
    {
        return  StationCodesMapper.selectAll();
    }

    /**
     * 获取所有的code
     * @param station_Id
     * @param param_Name
     * @return
     */
    @Override
    @TargetDataSource(name="datadb")
    public List<StationCodes> getStationCodes(String station_Id, String param_Name) {
        return StationCodesMapper.getStationCodes(station_Id,param_Name);
    }
}