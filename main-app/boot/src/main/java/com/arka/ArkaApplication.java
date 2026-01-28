package com.arka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ArkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArkaApplication.class, args);
	}

}
