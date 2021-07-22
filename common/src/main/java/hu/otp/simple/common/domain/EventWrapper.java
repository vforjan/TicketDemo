package hu.otp.simple.common.domain;

import java.util.List;

/**
 * Event wrapper POJO
 * 
 * @author vforjan
 *
 */
public class EventWrapper {

	private List<Event> data;
	private boolean success;

	public List<Event> getData() {
		return data;
	}

	public void setData(List<Event> data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
