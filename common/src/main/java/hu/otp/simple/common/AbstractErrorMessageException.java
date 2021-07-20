package hu.otp.simple.common;

public abstract class AbstractErrorMessageException extends RuntimeException {

	/** Serial */
	private static final long serialVersionUID = 3002557979393278754L;
	private ErrorMessages errorMessage;

	public ErrorMessages getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessages errorMessage) {
		this.errorMessage = errorMessage;
	}

	public AbstractErrorMessageException() {
		super();
	}

	public AbstractErrorMessageException(ErrorMessages errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

}