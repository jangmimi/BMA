package com.ap4j.bma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BmaApplication {
	public static void main(String[] args) {
		SpringApplication.run(BmaApplication.class, args);
	}

}
