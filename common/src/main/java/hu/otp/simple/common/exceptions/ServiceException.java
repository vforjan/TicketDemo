package hu.otp.simple.common.exceptions;

import hu.otp.simple.common.AbstractErrorMessageException;
import hu.otp.simple.common.ErrorMessages;

public class ServiceException extends AbstractErrorMessageException {

	/** Serial */
	private static final long serialVersionUID = 7231927706252827337L;

	public ServiceException(ErrorMessages errorMessage) {
		super(errorMessage);
	}

}
