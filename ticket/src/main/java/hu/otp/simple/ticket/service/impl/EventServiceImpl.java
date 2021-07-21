package hu.otp.simple.ticket.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.domain.SeatInfo;
import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.common.dtos.UserPaymentDto;
import hu.otp.simple.common.exceptions.ReservationException;
import hu.otp.simple.ticket.clients.CoreClient;
import hu.otp.simple.ticket.clients.PartnerClient;
import hu.otp.simple.ticket.service.EventService;

@Service
public class EventServiceImpl implements EventService {
	private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	private PartnerClient partnerClient;

	@Autowired
	private CoreClient coreClient;

	@Override
	public EventInfo queryEventInfoFromPartnerByEventId(long eventId) {
		log.info("Query event info by id = {}", eventId);
		return partnerClient.queryEvent(eventId);

	}

	@Override
	public List<Event> queryEventsFromPartner() {
		log.info("Query all events.");
		return partnerClient.queryEvents();
	}

	@Override
	public ReserveDto reserveAndPay(long eventId, long seatId, String cardId, String token) {

		//// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Check clients
		if (!partnerClient.isAlive()) {
			log.error("A partner module nem elérhető.");
			throw new ReservationException(ErrorMessages.PARTNER_MODULE_NOT_ALIVE);

		}
		if (!coreClient.isAlive()) {
			log.error("A Core module nem elérhető.");
			throw new ReservationException(ErrorMessages.CORE_MODULE_NOT_ALIVE);
		}

		log.info("Query event info by id = {}", eventId);

		//// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Check event
		EventInfo info = partnerClient.queryEvent(eventId);
		if (info == null) {
			log.info("Nincs ilyen esemény. EventId = {}", eventId);
			throw new ReservationException(ErrorMessages.EVENT_NOT_EXIST);
		}

		//// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Check date
		Event eventDescription = partnerClient.queryEventDescription(eventId);
		String startDate = eventDescription.getEndTimeStamp();
		if (!StringUtils.hasLength(startDate)) {
			log.info("Érvénytelen esemény kezdeti időpont.");
			throw new ReservationException(ErrorMessages.INVALID_EVENT);
		}

		Date eventStartTime = new Date(Long.parseLong(startDate) * 1000);
		if (eventStartTime.before(new Date())) {
			log.info("Érvénytelen esemény kezdeti időpont.");
			throw new ReservationException(ErrorMessages.EVENT_STARTED);
		}

		//// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Check seat
		final String finalSeatId = "S" + seatId;
		SeatInfo seatInfo = info.getSeats().stream().filter(s -> finalSeatId.equals(s.getId())).findFirst().orElse(null);
		if (seatInfo == null) {
			log.info("Érvénytelen szék azonosító. seatId = {}", seatId);
			throw new ReservationException(ErrorMessages.SEAT_NOT_EXIST);
		}

		if (seatInfo.isReserved()) {
			log.info("A szék már foglalt. seatId = {}", seatId);
			throw new ReservationException(ErrorMessages.RESERVED_SEAT);
		}

		//// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Check user card
		int price = seatInfo.getPrice();
		UserPaymentDto paymentDto = coreClient.validateUserPayment(token, cardId, price);
		if (!paymentDto.isSuccess()) {
			log.info("Fizetés ellenörzése közben fellépett hiba. Hiba= {}", paymentDto.getOptionalError());
			throw new ReservationException(paymentDto.getOptionalError());
		} else {
			log.info("Sikeres fizetés validálás. Fizetés véglegesítése.");
			ReserveDto reservation = partnerClient.reserve(eventId, seatId);
			return reservation;
		}

	}

}
