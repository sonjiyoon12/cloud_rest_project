package com.cloud.cloud_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CloudRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudRestApplication.class, args);
	}

}
