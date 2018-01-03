package com.bom.dataservice;

import com.bom.dataservice.model.EquInfo;
import com.bom.dataservice.service.EquInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataserviceApplicationTests {

	@Autowired
	private EquInfoService equInfoService;
	@Test
	public void contextLoads() {

//	 List<EquInfo> list= equInfoService.getListEquInfo("2022,2014,2021,2098");
//
//	 System.out.print("123");

	}

}
