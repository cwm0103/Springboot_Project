package com.bom.domain.mix.service.imp;

import com.bom.domain.BaseService;
import com.bom.domain.mix.model.MixHour;
import com.bom.domain.mix.service.IMixHourService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class MixHourServiceImp extends BaseService<MixHour> implements IMixHourService {
    @Autowired
    private com.bom.domain.mix.dao.MixHourMapper MixHourMapper;
}