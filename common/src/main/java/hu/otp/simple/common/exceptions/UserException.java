package hu.otp.simple.common.exceptions;

import hu.otp.simple.common.AbstractErrorMessageException;
import hu.otp.simple.common.ErrorMessages;

public class UserException extends AbstractErrorMessageException {

	/** Serial number */
	private static final long serialVersionUID = -4141128550890392019L;

	public UserException(ErrorMessages error) {
		super(error);
	}
}
