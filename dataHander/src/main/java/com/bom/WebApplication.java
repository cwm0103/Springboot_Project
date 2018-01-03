package com.bom;

import com.bom.datasource.DynamicDataSourceRegister;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DynamicDataSourceRegister.class}) // 注册动态多数据源
public class WebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(WebApplication.class, args);

//		SpringApplicationBuilder builder = new SpringApplicationBuilder(WebApplication.class);
//		//修改Banner的模式为OFF
//		builder.bannerMode(Banner.Mode.OFF).run(args);
	}
}
