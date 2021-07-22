package hu.otp.simple.common.exceptions;

import hu.otp.simple.common.AbstractErrorMessageException;
import hu.otp.simple.common.ErrorMessages;

public class EventException extends AbstractErrorMessageException {

	/** Serial */
	private static final long serialVersionUID = 8282126799055782374L;

	public EventException(ErrorMessages errorMessage) {
		super(errorMessage);
	}

}
