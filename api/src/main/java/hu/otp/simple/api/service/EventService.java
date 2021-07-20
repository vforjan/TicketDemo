package hu.otp.simple.api.service;

import java.util.List;

import hu.otp.simple.common.domain.EventInfo;

/**
 * Event service for query event informations.
 * 
 * @author vforjan
 *
 */
public interface EventService {
	/**
	 * Query event datails from the ticket system.
	 * 
	 * @param eventId the id of the event
	 * @return the actual EventInfo
	 */
	EventInfo queryEventInfoByEventId(long eventId);

	/**
	 * Query <code>list</code> of event from the ticket system.
	 * 
	 * @return <code>lisr</code> of EventInfo
	 */
	List<EventInfo> queryEvents();

}
