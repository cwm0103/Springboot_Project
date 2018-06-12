package com.rick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class SpringBootDruidMultiDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDruidMultiDbApplication.class, args);
	}
}
