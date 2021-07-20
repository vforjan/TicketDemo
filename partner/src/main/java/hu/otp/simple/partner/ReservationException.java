package hu.otp.simple.partner;

public class ReservationException extends RuntimeException {
	/** Serial */
	private static final long serialVersionUID = -7157477661460467110L;
	int ErrorCode;

	public int getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(int errorCode) {
		ErrorCode = errorCode;
	}

	public ReservationException(int errorCode) {
		super();
		ErrorCode = errorCode;
	}

}
