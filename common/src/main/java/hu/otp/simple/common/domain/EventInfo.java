package hu.otp.simple.common.domain;

import java.util.List;

/**
 * Event info POJO
 * 
 * @author vforjan
 *
 */
public class EventInfo {

	private Long eventId;
	private List<SeatInfo> seats;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public List<SeatInfo> getSeats() {
		return seats;
	}

	public void setSeats(List<SeatInfo> seats) {
		this.seats = seats;
	}

}
