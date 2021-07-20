package hu.otp.simple.partner.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.simple.partner.domain.Reserve;
import hu.otp.simple.partner.service.ReservationService;
import hu.otp.simple.partner.utils.ResourceHandlingUtils;

@RestController
@RequestMapping("/partner")
public class PartnerController {

	@Autowired
	private ReservationService reservationService;

	@GetMapping("/getEvents")
	public ResponseEntity<String> getEvents() {
		String content = ResourceHandlingUtils.getContentOfEventsFromFileResource();

		// TODO validation
		return ResponseEntity.status(HttpStatus.OK).body(content);
	}

	@GetMapping("/getEvent")
	public ResponseEntity<String> getEvent(@RequestParam("id") long id) {

		String content = ResourceHandlingUtils.getContentOfEventByIdFromFileResource(id);

		return ResponseEntity.status(HttpStatus.OK).body(content);

	}

	@PostMapping("/reserve")
	public ResponseEntity<Reserve> getEvent(@RequestParam("EventId") long eventId, @RequestParam("SeatId") long seatId) {
		Reserve reserve = reservationService.reserve(eventId, seatId);
		if (reserve == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reserve);
		}

		return ResponseEntity.status(HttpStatus.OK).body(reserve);

	}

	@GetMapping("/reserve2")
	public ResponseEntity<Reserve> getEvent2() {
		Reserve reserve = reservationService.reserve(new Long(10), new Long(3));

		return ResponseEntity.status(HttpStatus.OK).body(reserve);

	}

	@GetMapping("/reserve3")
	public ResponseEntity<Reserve> getEvent3() {
		Reserve reserve = reservationService.reserve(new Long(3), new Long(12));
		if (reserve == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reserve);
		}

		return ResponseEntity.status(HttpStatus.OK).body(reserve);

	}

	@GetMapping("/reserve4")
	public ResponseEntity<Reserve> getEvent4() {
		Reserve reserve = reservationService.reserve(new Long(3), new Long(9));
		if (reserve == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reserve);
		}

		return ResponseEntity.status(HttpStatus.OK).body(reserve);

	}
}
