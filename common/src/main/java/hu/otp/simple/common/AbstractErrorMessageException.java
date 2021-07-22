package hu.otp.simple.common;

public abstract class AbstractErrorMessageException extends RuntimeException {

	/** Serial */
	private static final long serialVersionUID = 3002557979393278754L;
	private final ErrorMessages errorMessage;

	public ErrorMessages getErrorMessage() {
		return errorMessage;
	}

	public AbstractErrorMessageException(ErrorMessages errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

}