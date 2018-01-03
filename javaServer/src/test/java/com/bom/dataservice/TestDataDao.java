package com.bom.dataservice;

import com.bom.dataservice.service.ABSDataService;
import com.bom.dataservice.service.HHDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.bom.dataservice.dao.HourDataDao;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.soap.Name;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-20
 * Time: 16:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDataDao {
    @Autowired
    private ApplicationContext context;


    public void test(){
        ABSDataService dataService = (ABSDataService) context.getBean("HH");
       // dataService.getData("1016","m340_RTU_UIQ_101_integrate_p_PV_CX_1016,ELE_GY_GY_5AH_DTZ1277_YC5_PV_CX_1016");
        //HourDataDao.getMonthData("1016","m340_RTU_UIQ_101_integrate_p_PV_CX_1016,ELE_GY_GY_5AH_DTZ1277_YC5_PV_CX_1016");
    }


}
