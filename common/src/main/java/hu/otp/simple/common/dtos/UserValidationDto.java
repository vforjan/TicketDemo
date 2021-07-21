package hu.otp.simple.common.dtos;

import hu.otp.simple.common.ErrorMessages;

public class UserValidationDto {

	private String token;
	private int userId;
	private boolean success;
	private ErrorMessages optionalError;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ErrorMessages getOptionalError() {
		return optionalError;
	}

	public void setOptionalError(ErrorMessages optionalError) {
		this.optionalError = optionalError;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
