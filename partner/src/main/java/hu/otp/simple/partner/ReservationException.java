package hu.otp.simple.partner;

import hu.otp.simple.common.AbstractErrorMessageException;
import hu.otp.simple.common.ErrorMessages;

public class ReservationException extends AbstractErrorMessageException {
	/** Serial */
	private static final long serialVersionUID = -7157477661460467110L;

	public ReservationException(ErrorMessages errorMessage) {
		super(errorMessage);
	}

}
