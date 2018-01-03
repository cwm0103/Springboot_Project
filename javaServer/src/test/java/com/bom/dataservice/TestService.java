package com.bom.dataservice;

import com.bom.dataservice.config.TargetDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by chenwangming on 2017/11/14.
 */
@Service
public class TestService {
    @Autowired
    private TestDao testDao;

    /**
     *不指定数据源使用默认数据源
     *@return
     */

    public List<UserRole> getList(){
        return testDao.getList();
    }

    /**
     *指定数据源
     *@return
     */

    @TargetDataSource("ds1")
    public List<UserRole> getListByDs1(){
        return testDao.getListByDs1();
    }


}
