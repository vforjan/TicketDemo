package hu.otp.simple.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.simple.api.client.TicketClient;
import hu.otp.simple.api.service.EventService;
import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;

@Service
public class EventServiceImpl implements EventService {
	private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	private TicketClient ticketClient;

	@Override
	public EventInfo queryEventInfoByEventId(long eventId) {
		log.info("Query event info by id = {}", eventId);
		return ticketClient.queryEvent(eventId);

	}

	@Override
	public List<Event> queryEvents() {
		log.info("Query all events.");
		return ticketClient.queryEvents();
	}

}
