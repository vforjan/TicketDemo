package hu.otp.simple.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.simple.api.client.CoreClient;
import hu.otp.simple.api.service.ApiService;
import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.common.dtos.UserValidationDto;
import hu.otp.simple.common.exceptions.UserException;

@Service
public class ApiServiceImpl implements ApiService {

	private static Logger log = LoggerFactory.getLogger(ApiServiceImpl.class);

	@Autowired
	private CoreClient coreClient;

	@Override
	public UserValidationDto validateUser(String token) {

		log.info("Validate user by token.");
		UserValidationDto dto = coreClient.validateUserToken(token);

		// TODO: handle null with another case
		if (dto == null || !dto.isSuccess()) {
			ErrorMessages message = dto.getOptionalError() == null ? ErrorMessages.USER_INVALIDATED : dto.getOptionalError();
			throw new UserException(message);
		}

		log.info("User validated successfully!");
		return dto;
	}

	@Override
	public ReserveDto payAttempt(long eventId, long seatId, long cardId) {
		// TODO Auto-generated method stub
		return null;
	}

}
