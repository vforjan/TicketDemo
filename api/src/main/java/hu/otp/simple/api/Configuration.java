package hu.otp.simple.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class Configuration {
	private static final Logger log = LoggerFactory.getLogger(Configuration.class);

	@Bean
	CommandLineRunner initDatabase() {

		return args -> {
			log.info("Application configured");
		};
	}
}