package hu.otp.simple.core.controller;

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
import hu.otp.simple.core.service.UserService;

@RestController
@RequestMapping("/core")
public class CoreController {

	private static final Logger log = LoggerFactory.getLogger(CoreController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/validate-token")
	public ResponseEntity<UserValidationDto> checkToken(@RequestParam("token") String token) {
		log.info("Felhasználói token ellenörzése.");
		UserValidationDto dto = userService.validateUserByUserToken(token);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@GetMapping("/payment-validation")
	public UserPaymentDto checkPayment(@RequestParam("payment") int payment, @RequestParam("token") String token, String cardId) {
		log.info("Fizetés ellenörzése.");
		return userService.preCheckPayment(token, cardId, payment);

	}

	@GetMapping("/heartbeat")
	public ResponseEntity<Integer> getHeartbeat(@RequestParam("heartbeat") Integer hb, @RequestParam("client") String client) {
		log.info("Ping fogadva a klienstől : {}", client);
		return ResponseEntity.status(HttpStatus.OK).body(hb + 2);

	}
}
