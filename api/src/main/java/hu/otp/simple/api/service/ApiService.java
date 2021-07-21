package hu.otp.simple.api.service;

import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.common.dtos.UserValidationDto;

public interface ApiService {

	UserValidationDto validateUser(String token);

	ReserveDto payAttempt(long eventId, long seatId, long cardId, String token);
}
