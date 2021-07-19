package hu.otp.simple.domain;

/**
 * Abstract class for user related entity handling.
 * 
 * @author vforjan
 *
 */

public abstract class BaseEntity implements HasUserId {

	/** The userId. */
	private Integer userId;

	@Override
	public Integer getUserId() {
		return userId;
	}

	@Override
	public void setUserId() {
		// TODO Auto-generated method stub

	}

}
