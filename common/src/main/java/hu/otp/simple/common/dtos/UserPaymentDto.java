package hu.otp.simple.common.dtos;

public class UserPaymentDto extends UserValidationDto {
	int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
