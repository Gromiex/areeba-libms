package com.example.libms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LibmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibmsApplication.class, args);
	}

}
