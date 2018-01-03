package com.bom.domain.data.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.model.StationTop;
import com.bom.domain.data.service.IStationTopService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class StationTopServiceImp extends BaseService<StationTop> implements IStationTopService {
    @Autowired
    private com.bom.domain.data.dao.StationTopMapper StationTopMapper;

    @TargetDataSource(name="datadb")
    public int save(StationTop entity) {
        return super.save(entity);
    }
    @TargetDataSource(name="datadb")
    public StationTop getStationDataBySiteIdAndType(String station_id,int type)
    {
        return  StationTopMapper.getStationDataBySiteIdAndType(station_id, type);
    }

    @Override
    @TargetDataSource(name="datadb")
    public int updateAll(StationTop entity) {
        return super.updateAll(entity);
    }

    @Override
    @TargetDataSource(name="datadb")
    public List<StationTop> selectAll() {
        return super.selectAll();
    }
}