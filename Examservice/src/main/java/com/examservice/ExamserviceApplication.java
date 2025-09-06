package com.examservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ExamserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamserviceApplication.class, args);
	}

}
