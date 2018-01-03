package com.filemanage;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//@SpringBootApplication
//public class FileserviceApplication  extends SpringBootServletInitializer  {
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		// 注意这里要指向原先用main方法执行的Application启动类
//		return builder.sources(FileserviceApplication.class);
//	}
//	public static void main(String[] args) {
//		SpringApplication.run(FileserviceApplication.class, args);
//	}
//
//
//}

@SpringBootApplication
public class FileserviceApplication   {
	public static void main(String[] args) {
		SpringApplication.run(FileserviceApplication.class, args);
	}
}

