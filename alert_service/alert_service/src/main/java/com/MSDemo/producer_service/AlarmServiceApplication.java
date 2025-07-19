package com.MSDemo.producer_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlarmServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AlarmServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// This method is called after the application context is loaded
		// You can perform any additional initialization here if needed
		System.out.println("Alert Service Application started successfully.");
	}
}
