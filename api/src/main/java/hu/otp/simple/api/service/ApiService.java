package hu.otp.simple.api.service;

import java.util.List;

import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.common.dtos.UserValidationDto;

public interface ApiService {

	UserValidationDto validateUser(String token);

	ReserveDto payAttempt(long eventId, long seatId, long cardId);
}
