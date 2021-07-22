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

	/**
	 * Attempt to reserve a seat and pay for it.
	 * 
	 * @param eventId the event id
	 * @param seatId the seta id
	 * @param cardId the users card id
	 * @param token the users token
	 * @return the result of the reservation
	 */
	ReserveDto reserveAndPay(long eventId, long seatId, String cardId, String token);

}
