package com.bom.domain.spvdata.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.spvdata.model.ResultHourSpv;
import com.bom.domain.spvdata.service.IResultHourSpvService;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class ResultHourSpvServiceImp extends BaseService<ResultHourSpv> implements IResultHourSpvService {
    @Autowired
    private com.bom.domain.spvdata.dao.ResultHourSpvMapper ResultHourSpvMapper;

    @Override
    @TargetDataSource(name="datadb")
    public int insertResultHourSpv(ResultHourSpv resultHourSpv) {
        return ResultHourSpvMapper.insertResultHour(resultHourSpv);
    }

    @Override
    @TargetDataSource(name="datadb")
    public int updateResultHourSpv(ResultHourSpv resultHourSpv) {
        return ResultHourSpvMapper.updateResultHour(resultHourSpv);
    }
}