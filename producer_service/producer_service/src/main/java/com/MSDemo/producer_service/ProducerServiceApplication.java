package com.MSDemo.producer_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerServiceApplication implements CommandLineRunner {

	@Autowired
	AlarmGenerator alarmGenerator;

	public static void main(String[] args) {
		SpringApplication.run(ProducerServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// This method is called after the application context is loaded
		// You can perform any additional initialization here if needed
		System.out.println("Producer Service Application started successfully.");
		alarmGenerator.generateAlarms();
	}
}
