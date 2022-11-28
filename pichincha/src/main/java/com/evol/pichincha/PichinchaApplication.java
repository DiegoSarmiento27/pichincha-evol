package com.evol.pichincha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableScheduling
@EnableWebFlux
@EnableR2dbcRepositories
@SpringBootApplication
public class PichinchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PichinchaApplication.class, args);
	}

}
