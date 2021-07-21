package hu.otp.simple.core.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.simple.common.dtos.UserPaymentDto;
import hu.otp.simple.common.dtos.UserValidationDto;
import hu.otp.simple.core.domain.User;
import hu.otp.simple.core.repository.UserRepository;
import hu.otp.simple.core.service.UserService;
import hu.otp.simple.core.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/core")
public class CoreController {

	private static final Logger log = LoggerFactory.getLogger(CoreController.class);

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
	public boolean checkAmount(@RequestParam("price") int price, @RequestParam("token") String token) {

		return userService.hasCardCoverage(10000, "C0001");

	}

	@GetMapping("/payment-validation")
	public UserPaymentDto checkPayment(@RequestParam("payment") int payment, @RequestParam("token") String token, String cardId) {
		log.info("Fizetés ellenörzése.");
		return userService.preCheckPayment(token, cardId, payment);

	}

	@GetMapping("/heartbeat")
	public ResponseEntity<Integer> getHeartbeat(@RequestParam("heartbeat") Integer hb, @RequestParam("client") String client) {
		log.info("Heartbeat request accepted from client: {}", client);
		return ResponseEntity.status(HttpStatus.OK).body(hb + 2);

	}
}
