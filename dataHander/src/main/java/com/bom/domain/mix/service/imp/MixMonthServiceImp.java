package com.bom.domain.mix.service.imp;

import com.bom.domain.BaseService;
import com.bom.domain.mix.model.MixMonth;
import com.bom.domain.mix.service.IMixMonthService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class MixMonthServiceImp extends BaseService<MixMonth> implements IMixMonthService {
    @Autowired
    private com.bom.domain.mix.dao.MixMonthMapper MixMonthMapper;
}