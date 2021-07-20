package hu.otp.simple.partner.service;

import hu.otp.simple.partner.domain.Reserve;

public interface ReservationService {

	public Reserve reserve(long eventId, long seatId);

}
