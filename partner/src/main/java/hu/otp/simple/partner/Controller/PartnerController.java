package hu.otp.simple.partner.Controller;

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

import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.partner.service.ReservationService;
import hu.otp.simple.partner.utils.ResourceHandlingUtils;

@RestController
@RequestMapping("/partner")
public class PartnerController {

	private static final Logger log = LoggerFactory.getLogger(PartnerController.class);

	@Autowired
	private ReservationService reservationService;

	@GetMapping("/getEvents")
	public ResponseEntity<List<Event>> getEvents() {
		log.info("Események lekérdezése.");
		List<Event> content = ResourceHandlingUtils.getContentOfEvents();
		// TODO validation
		return ResponseEntity.status(HttpStatus.OK).body(content);
	}

	@GetMapping("/getEvent")
	public ResponseEntity<EventInfo> getEvent(@RequestParam("id") long id) {
		log.info("Esemény lekérdezése. Id= {}", id);
		EventInfo content = ResourceHandlingUtils.getEventInfoById(id);
		return ResponseEntity.status(HttpStatus.OK).body(content);

	}

	@GetMapping("/getEventDescription")
	public ResponseEntity<Event> getEventDetails(@RequestParam("id") long id) {
		log.info("Esemény leírásának lekérdezése.");
		Event content = ResourceHandlingUtils.getEventById(id);
		return ResponseEntity.status(HttpStatus.OK).body(content);
	}

	@PostMapping("/reserve")
	public ResponseEntity<ReserveDto> reserve(@RequestParam("EventId") long eventId, @RequestParam("SeatId") long seatId) {
		log.info("Hely foglalási kisérlet. EventId = {}, SeatId = {}", eventId, seatId);

		ReserveDto reserveDto = reservationService.reserveDto(eventId, seatId);
		if (reserveDto == null) {

			log.info("Sikertelen hely foglalás EventId = {}, SeatId = {}", eventId, seatId);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reserveDto);
		}

		return ResponseEntity.status(HttpStatus.OK).body(reserveDto);

	}

	@GetMapping("/heartbeat")
	public ResponseEntity<Integer> getHeartbeat(@RequestParam("heartbeat") Integer hb, @RequestParam("client") String client) {
		log.info("Heartbeat request accepted from client: {}", client);
		return ResponseEntity.status(HttpStatus.OK).body(hb + 1);

	}

	// FIXME: just for testing

	@GetMapping("/reserve2")
	public ResponseEntity<ReserveDto> getEvent2() {
		ReserveDto reserveDto = reservationService.reserveDto(new Long(10), new Long(3));

		return ResponseEntity.status(HttpStatus.OK).body(reserveDto);

	}

	@GetMapping("/reserve3")
	public ResponseEntity<ReserveDto> getEvent3() {
		ReserveDto reserveDto = reservationService.reserveDto(new Long(3), new Long(12));
		if (reserveDto == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reserveDto);
		}

		return ResponseEntity.status(HttpStatus.OK).body(reserveDto);

	}

	@GetMapping("/reserve4")
	public ResponseEntity<ReserveDto> getEvent4() {
		ReserveDto reserveDto = reservationService.reserveDto(new Long(3), new Long(9));
		if (reserveDto == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reserveDto);
		}

		return ResponseEntity.status(HttpStatus.OK).body(reserveDto);

	}

	@GetMapping("/heartbeat")
	public boolean heartbeat() {
		return true;

	}
}
