package com.bjly.webapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.bjly"})//(scanBasePackages = {"com.bjly.webservice.userManage","com.bjly.webmapper.userManage"})
public class WebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApiApplication.class, args);
	}
}
