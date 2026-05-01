package com.soa.soaventas;

import org.springframework.boot.SpringApplication;

public class TestSoaventasApplication {

	public static void main(String[] args) {
		SpringApplication.from(SoaventasApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
