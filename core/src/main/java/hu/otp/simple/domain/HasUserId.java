package hu.otp.simple.domain;

/**
 * Interface for entities with UserId.
 * 
 * @author vforjan
 *
 */
public interface HasUserId {

	/**
	 * Get the entity's id.
	 * 
	 * @return the entity's I
	 */
	Integer getUserId();

	/**
	 * Set the UserId.
	 * 
	 */
	void setUserId();

}
