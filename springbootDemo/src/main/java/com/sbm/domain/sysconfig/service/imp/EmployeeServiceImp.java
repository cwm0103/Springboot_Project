package com.sbm.domain.sysconfig.service.imp;

import com.sbm.domain.BaseService;
import com.sbm.domain.sysconfig.model.Employee;
import com.sbm.domain.sysconfig.service.IEmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class EmployeeServiceImp extends BaseService<Employee> implements IEmployeeService {
    @Autowired
    private com.sbm.domain.sysconfig.dao.EmployeeMapper EmployeeMapper;
}