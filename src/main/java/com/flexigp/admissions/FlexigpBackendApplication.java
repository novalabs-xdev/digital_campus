package com.flexigp.admissions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlexigpBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlexigpBackendApplication.class, args);
	}

}
