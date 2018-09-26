package com.oper;

import com.oper.entity.PubView;
import com.oper.repository.PubViewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReporttoolApplicationTests {

	@Autowired
	private  PubViewRepository pubViewRepository;
	@Test
	public void contextLoads() {
		List<PubView> all = pubViewRepository.findAll();
		System.out.println(all);
	}

}
