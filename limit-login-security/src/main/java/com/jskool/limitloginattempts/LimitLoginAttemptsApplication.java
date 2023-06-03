package com.jskool.limitloginattempts;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
		ManagementWebSecurityAutoConfiguration.class})
@EnableScheduling
public class LimitLoginAttemptsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LimitLoginAttemptsApplication.class, args);
	}

}
