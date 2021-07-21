package hu.otp.simple.ticket.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.ticket.service.EventService;
import hu.otp.simple.ticket.service.impl.EventServiceImpl;

/**
 * Ticket module controller.
 * 
 * @author vforjan
 *
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {
	private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	private EventService eventService;

	@GetMapping("/getEvents")
	public ResponseEntity<List<Event>> getEvents() {
		log.info("Események lekérdezése.");
		List<Event> events = eventService.queryEventsFromPartner();
		if (CollectionUtils.isEmpty(events)) {
			log.info("The partner has no events.");
			ResponseEntity.status(HttpStatus.NO_CONTENT).body(events);
		}

		return ResponseEntity.status(HttpStatus.OK).body(events);
	}

	@GetMapping("/getEvent")
	public ResponseEntity<EventInfo> getEvent(@RequestParam("id") long id) {
		log.info("Esemény részleteinek lekérdezése. Id= {}", id);
		EventInfo event = eventService.queryEventInfoFromPartnerByEventId(id);
		if (event == null) {
			log.info("Not found event for id = {}", id);
			ResponseEntity.status(HttpStatus.NO_CONTENT).body(event);
		}
		return ResponseEntity.status(HttpStatus.OK).body(event);
	}

	@GetMapping("/reserve")
	public ResponseEntity<ReserveDto> reserveEvent(@RequestParam("EventId") long eventId, @RequestParam("SeatId") long seatId,
			@RequestParam("CardId") String cardId, @RequestParam("UserToken") String token) {
		log.info("Pay attempt with seat reservation. EventId = {}, SeatId = {}, CardId = {}", eventId, seatId, cardId);

		ReserveDto reserve = eventService.reserveAndPay(eventId, seatId, cardId, token);
		if (reserve == null) {

			log.info("Sikertelen hely foglalás EventId = {}, SeatId = {}, CardId = {}", eventId, seatId, cardId);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(reserve);
		}

		return ResponseEntity.status(HttpStatus.OK).body(reserve);

	}

}
