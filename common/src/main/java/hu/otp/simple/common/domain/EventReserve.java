package hu.otp.simple.common.domain;

/**
 * Event reservation POJO
 * 
 * @author vforjan
 *
 */
public class EventReserve {

	EventInfo data;
	boolean success;

	public EventInfo getData() {
		return data;
	}

	public void setData(EventInfo data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
