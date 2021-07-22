package hu.otp.simple.common.dtos;

public class ReservationErrorDto {

	private boolean success = false;
	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public ReservationErrorDto(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

}
