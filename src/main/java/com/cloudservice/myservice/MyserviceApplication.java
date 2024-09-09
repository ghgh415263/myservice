package com.cloudservice.myservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MyserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyserviceApplication.class, args);
	}

}
