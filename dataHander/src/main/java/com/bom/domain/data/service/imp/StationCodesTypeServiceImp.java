package com.bom.domain.data.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.model.StationCodesType;
import com.bom.domain.data.service.IStationCodesTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class StationCodesTypeServiceImp extends BaseService<StationCodesType> implements IStationCodesTypeService {
    @Autowired
    private com.bom.domain.data.dao.StationCodesTypeMapper StationCodesTypeMapper;

    @Override
    @TargetDataSource(name="datadb")
    public List<StationCodesType> selectAll() {
        return super.selectAll();
    }
}