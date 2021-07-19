package hu.otp.simple.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);

	@GetMapping("/hello")
	public String hello() {

		log.info("Hello");

		return "Hello!";

	}
}
