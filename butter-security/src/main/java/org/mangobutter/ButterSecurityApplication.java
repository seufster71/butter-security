package org.mangobutter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.mangobutter","org.testo"})
public class ButterSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ButterSecurityApplication.class, args);
	}
}
