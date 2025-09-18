package com.auth;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthServiceApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load(); // loads from .env automatically
		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}
