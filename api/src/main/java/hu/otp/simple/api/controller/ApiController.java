package hu.otp.simple.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.simple.api.service.ApiService;
import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.common.dtos.UserValidationDto;

@RestController
@RequestMapping("/api")
public class ApiController {
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private ApiService apiService;

	@GetMapping("/getEvents")
	public ResponseEntity<List<EventInfo>> getEvents() {
		log.info("Események lekérdezése.");
		List<EventInfo> events = apiService.getEvents();
		// TODO validation
		return ResponseEntity.status(HttpStatus.OK).body(events);
	}

	@GetMapping("/validateToken")
	public ResponseEntity<UserValidationDto> validate() {
		String token = "dGVzenQuYWxhZGFyQG90cG1vYmlsLmNvbSYxMDAwJjNBRTVFOTY1OEZCRDdENDA0OEJENDA4MjBCN0QyMjdE";
		UserValidationDto dto = apiService.validateUser(token);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@GetMapping("/getEvent")
	public ResponseEntity<EventInfo> getEvent(@RequestParam("id") long id) {
		log.info("Esemény részleteinek lekérdezése. Id= {}", id);
		EventInfo event = apiService.getEvent();
		return ResponseEntity.status(HttpStatus.OK).body(event);

	}

	@PostMapping("/pay")
	public ResponseEntity<ReserveDto> getEvent(@RequestParam("EventId") long eventId, @RequestParam("SeatId") long seatId,
			@RequestParam("CardId") long cardId) {
		log.info("Fizetési kisérlet helyfoglalással. EventId = {}, SeatId = {}, CardId = {}", eventId, seatId, cardId);

		ReserveDto reserve = apiService.payAttempt(eventId, seatId, cardId);
		if (reserve == null) {

			log.info("Sikertelen hely foglalás EventId = {}, SeatId = {}, CardId = {}", eventId, seatId, cardId);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reserve);
		}

		return ResponseEntity.status(HttpStatus.OK).body(reserve);

	}
}
