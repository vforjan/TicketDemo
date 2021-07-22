package hu.otp.simple.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.simple.api.service.ApiService;
import hu.otp.simple.api.service.EventService;
import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.common.dtos.UserValidationDto;

@RestController
@RequestMapping("/api")
public class ApiController {
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private ApiService apiService;

	@Autowired
	private EventService eventService;

	@GetMapping("/getEvents")
	public ResponseEntity<List<Event>> getEvents(@RequestHeader("User-Token") String token) {
		log.info("Események lekérdezése.");
		UserValidationDto dto = apiService.validateUser(token);
		if (!dto.isSuccess()) {
			log.warn("Sikertelen felhasználó authentikáció.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<Event> events = eventService.queryEvents();
		return ResponseEntity.status(HttpStatus.OK).body(events);
	}

	@GetMapping("/getEvent")
	public ResponseEntity<EventInfo> getEvent(@RequestHeader("User-Token") String token, @RequestParam("id") long id) {
		log.info("Esemény részleteinek lekérdezése. Id= {}", id);

		UserValidationDto dto = apiService.validateUser(token);
		if (!dto.isSuccess()) {
			log.warn("Sikertelen felhasználó authentikáció.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		EventInfo event = eventService.queryEventInfoByEventId(id);
		return ResponseEntity.status(HttpStatus.OK).body(event);

	}

	@PostMapping("/pay")
	public ResponseEntity<ReserveDto> payAttempt(@RequestHeader("User-Token") String token, @RequestParam("EventId") long eventId,
			@RequestParam("SeatId") long seatId, @RequestParam("CardId") long cardId) {
		log.info("Fizetési kisérlet helyfoglalással. EventId = {}, SeatId = {}, CardId = {}", eventId, seatId, cardId);

		UserValidationDto dto = apiService.validateUser(token);
		if (!dto.isSuccess()) {
			log.warn("Sikertelen felhasználó authentikáció.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		ReserveDto reserve = apiService.payAttempt(eventId, seatId, cardId, token);
		if (reserve == null) {

			log.info("Sikertelen hely foglalás EventId = {}, SeatId = {}, CardId = {}", eventId, seatId, cardId);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reserve);
		}

		return ResponseEntity.status(HttpStatus.OK).body(reserve);

	}
}
