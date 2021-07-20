package hu.otp.simple.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class CoreApplication {

	public static void main(String... args) {

		SpringApplication.run(CoreApplication.class, args);
	}
}
