package com.bom.dataservice;

import com.bom.dataservice.config.DynamicDataSourceRegister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by chenwangming on 2017/11/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestController {
    @Autowired
    private TestService testService;
    @Test
    public void test(){
//           for(UserRole d:testService.getList()){
//                  System.out.println("id:"+d.getId()+";role_id:"+d.getRole_id()+";user_id:"+d.getUser_id());
//           }
        /*for(UserRole d:testService.getListByDs1()){
            System.out.println("id:"+d.getId()+";role_id:"+d.getRole_id()+";user_id:"+d.getUser_id());
        }*/
       System.out.print("OK");
    }
}
