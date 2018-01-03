package com.bom.domain.mix.service.imp;

import com.bom.domain.BaseService;
import com.bom.domain.mix.model.Func;
import com.bom.domain.mix.service.IFuncService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class FuncServiceImp extends BaseService<Func> implements IFuncService {
    @Autowired
    private com.bom.domain.mix.dao.FuncMapper FuncMapper;
}