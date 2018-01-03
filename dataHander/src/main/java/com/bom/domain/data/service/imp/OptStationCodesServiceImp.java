package com.bom.domain.data.service.imp;

import com.bom.domain.BaseService;
import com.bom.domain.data.model.OptStationCodes;
import com.bom.domain.data.service.IOptStationCodesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class OptStationCodesServiceImp extends BaseService<OptStationCodes> implements IOptStationCodesService {
    @Autowired
    private com.bom.domain.data.dao.OptStationCodesMapper OptStationCodesMapper;
}