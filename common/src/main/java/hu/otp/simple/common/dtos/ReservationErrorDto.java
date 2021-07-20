package hu.otp.simple.common.dtos;

public class ReservationErrorDto {

	private boolean success = false;
	private int ErrorCode;

	public int getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(int errorCode) {
		ErrorCode = errorCode;
	}

	public ReservationErrorDto(int errorCode) {
		super();
		ErrorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

}
