package hu.otp.simple.common.dtos;

public class ReserveDto {

	private Long reserver;
	private boolean success;

	public ReserveDto(Long reserver, boolean success) {
		super();
		this.reserver = reserver;
		this.success = success;
	}

	public Long getReserver() {
		return reserver;
	}

	public void setReserver(Long reserver) {
		this.reserver = reserver;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ReserveDto() {
		super();
	}

}
