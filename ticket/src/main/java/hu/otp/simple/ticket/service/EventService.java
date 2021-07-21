package hu.otp.simple.ticket.service;

import java.util.List;

import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.dtos.ReserveDto;

/**
 * Event service for query event informations.
 * 
 * @author vforjan
 *
 */
public interface EventService {
	/**
	 * Query event datails from partner.
	 * 
	 * @param eventId the id of the event
	 * @return the actual EventInfo
	 */
	EventInfo queryEventInfoFromPartnerByEventId(long eventId);

	/**
	 * Query <code>list</code> of event from partner.
	 * 
	 * @return <code>lisr</code> of EventInfo
	 */
	List<Event> queryEventsFromPartner();

	ReserveDto reserveAndPay(long eventId, long seatId, String cardId, String token);

}
