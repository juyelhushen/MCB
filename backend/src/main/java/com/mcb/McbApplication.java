package com.mcb;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "MCB Apis",version = "1.0",description = "mcb api documentation"))
public class McbApplication {

	public static void main(String[] args) {
		SpringApplication.run(McbApplication.class, args);
	}

}
