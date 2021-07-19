package hu.otp.simple.domain;

public class ValidationDto {

	private String email;
	private Integer userId;
	private String deviceHash;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDeviceHash() {
		return deviceHash;
	}

	public void setDeviceHash(String deviceHash) {
		this.deviceHash = deviceHash;
	}

	@Override
	public String toString() {
		return "ValidationDto [email=" + email + ", userId=" + userId + ", deviceHash=" + deviceHash + "]";
	}

}
