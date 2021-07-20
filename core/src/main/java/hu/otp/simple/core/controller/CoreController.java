package hu.otp.simple.core.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.simple.common.dtos.UserValidationDto;
import hu.otp.simple.core.domain.User;
import hu.otp.simple.core.repository.UserRepository;
import hu.otp.simple.core.service.UserService;

@RestController
@RequestMapping("/core")
public class CoreController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {

		List<User> users = new LinkedList<User>();
		users = userRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		System.out.println(users);
		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	@GetMapping("/validate-token")
	public ResponseEntity<UserValidationDto> checkToken(@RequestParam("token") String token) {

		UserValidationDto dto = userService.validateUserByUserToken(token);

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@GetMapping("/cardvalidation")
	public boolean checkCard() {

		User user = userRepository.findByUserId(1000);
		return userService.isUserCardOwner(user, "C0001");

	}

	@GetMapping("/payvalidation")
	public boolean checkAmount() {

		return userService.hasCardCoverage(10000, "C0001");

	}

	@GetMapping("/hello")
	public String heartbeat() {
		return "Hello from the core!";

	}
}
