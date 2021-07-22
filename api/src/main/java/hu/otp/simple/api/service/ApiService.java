package hu.otp.simple.api.service;

import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.common.dtos.UserValidationDto;

/**
 * Api service interface
 * 
 * @author vforjan
 *
 */
public interface ApiService {

	/**
	 * Validate user with a core module call.
	 * 
	 * @param token the user token
	 * @return <code>UserValidationDto</code> witch represent the result of the validation
	 */
	UserValidationDto validateUser(String token);

	/**
	 * User indicated pay attempt for reserve a seat in an event with an already stored bank card.
	 * 
	 * @param eventId the actual event id
	 * @param seatId the seatid for reservation
	 * @param cardId the user's stored card's id
	 * @param token the user token
	 * @return <code>ReserveDto</code> witch represent the result of the reservation event
	 */
	ReserveDto payAttempt(long eventId, long seatId, long cardId, String token);
}
