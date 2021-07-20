package hu.otp.simple.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class ApiApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiApplication.class, args);
	}
}
