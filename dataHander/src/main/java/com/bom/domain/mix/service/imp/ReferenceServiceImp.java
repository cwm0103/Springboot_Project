package com.bom.domain.mix.service.imp;

import com.bom.domain.BaseService;
import com.bom.domain.mix.model.Reference;
import com.bom.domain.mix.service.IReferenceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class ReferenceServiceImp extends BaseService<Reference> implements IReferenceService {
    @Autowired
    private com.bom.domain.mix.dao.ReferenceMapper ReferenceMapper;
}