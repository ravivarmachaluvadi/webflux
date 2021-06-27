package com.example.springboot.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WebfluxApplication implements CommandLineRunner {


	@Autowired
	Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//System.setProperty("reactor.netty.ioWorkerCount", "100");
		System.getProperty("reactor.netty.ioWorkerCount");

		System.out.println();
	}
}
