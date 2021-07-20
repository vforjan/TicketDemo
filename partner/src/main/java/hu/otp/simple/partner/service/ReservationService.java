package hu.otp.simple.partner.service;

import hu.otp.simple.common.dtos.ReserveDto;

public interface ReservationService {

	public ReserveDto reserveDto(long eventId, long seatId);

}
