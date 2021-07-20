package hu.otp.simple.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class TicketApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(TicketApplication.class, args);
	}

}
