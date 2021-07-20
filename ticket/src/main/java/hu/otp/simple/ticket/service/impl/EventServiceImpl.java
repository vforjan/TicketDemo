package hu.otp.simple.ticket.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.domain.SeatInfo;
import hu.otp.simple.common.dtos.ReserveDto;
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
	public ReserveDto reserveAndPay(long eventId, long seatId, long cardId) {

		// TODO: check payment first, use core method for check
		// TODO: than try to reserve, use parner's method for check
		EventInfo event = queryEventInfoFromPartnerByEventId(eventId);

		return null;
	}

	// private boolean checkSeat(long seatId, List<SeatInfo> seats) {
	// seats.stream().anyMatch(t->seatId. t.getId())
	//
	// }

}
