package com.bom.domain.mix.service.imp;

import com.bom.domain.BaseService;
import com.bom.domain.mix.model.MixDay;
import com.bom.domain.mix.service.IMixDayService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class MixDayServiceImp extends BaseService<MixDay> implements IMixDayService {
    @Autowired
    private com.bom.domain.mix.dao.MixDayMapper MixDayMapper;
}