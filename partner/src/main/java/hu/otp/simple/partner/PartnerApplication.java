package hu.otp.simple.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class PartnerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PartnerApplication.class, args);
	}

}
