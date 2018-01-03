package com.bom.domain.mix.service.imp;

import com.bom.domain.BaseService;
import com.bom.domain.mix.model.MixYear;
import com.bom.domain.mix.service.IMixYearService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class MixYearServiceImp extends BaseService<MixYear> implements IMixYearService {
    @Autowired
    private com.bom.domain.mix.dao.MixYearMapper MixYearMapper;
}