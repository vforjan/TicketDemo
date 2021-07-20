package hu.otp.simple.core.service.impl;

import java.util.Base64;

import hu.otp.simple.core.domain.UserToken;
import hu.otp.simple.core.domain.ValidationDto;

public class ValidationUtils {

	public static ValidationDto getEncodedDataFromToken(String token) {

		byte[] decodedBytes = Base64.getDecoder().decode(token);
		String decodedString = new String(decodedBytes);
		String[] params = decodedString.split("&");

		ValidationDto dto = new ValidationDto();
		dto.setEmail(params[0]);
		int userId;
		try {
			userId = Integer.parseInt(params[1]);
		} catch (NumberFormatException e) {
			userId = -1;
		}

		dto.setUserId(userId);
		dto.setDeviceHash(params[2]);

		return dto;

	}
}
