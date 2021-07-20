package hu.otp.simple.core.service.impl;

import hu.otp.simple.common.ErrorMessages;

public class UserException extends RuntimeException {

	/** Serial number */
	private static final long serialVersionUID = -4141128550890392019L;
	ErrorMessages error;

	public UserException(ErrorMessages error) {
		super();
		this.error = error;
	}

	public ErrorMessages getError() {
		return error;
	}

}
