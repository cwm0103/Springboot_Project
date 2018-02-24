package com.imoo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
//	@Bean
//	public FilterRegistrationBean registrationBean()
//	{
//		FilterRegistrationBean bean=new FilterRegistrationBean();
//		bean.addUrlPatterns("/*");
//		bean.setFilter(new CrosFilter());
//		return bean;
//	}
}
