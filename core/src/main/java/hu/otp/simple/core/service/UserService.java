package hu.otp.simple.core.service;

import hu.otp.simple.common.dtos.UserPaymentDto;
import hu.otp.simple.common.dtos.UserValidationDto;
import hu.otp.simple.core.domain.User;

/**
 * User validation interface.
 * 
 * @author vforjan
 *
 */

public interface UserService {

	/**
	 * Validate a user by it's token.
	 * 
	 * @param token the user token
	 * @return the UserValidationDto contains the result of the validation
	 */
	UserValidationDto validateUserByUserToken(String token);

	/**
	 * Check if a user has a specific card by its id.
	 * 
	 * @param user the user
	 * @param cardId the card id
	 * @return <code>true</code> if the user has the card, otherwise <code>false</code>
	 */
	boolean isUserCardOwner(User user, String cardId);

	/**
	 * Check if a card has enough coverage for a transaction.
	 * 
	 * @param price
	 * @param cardId the card id
	 * @return <code>true</code> if there is enough coverage in the card, otherwise <code>false</code>
	 */
	boolean hasCardCoverage(int price, String cardId);

	/**
	 * Check if a payment can acceptable.
	 * 
	 * @param token the user token
	 * @param cardId the card id
	 * @param payment the actual amount of needed money
	 * @return the validation of the payment
	 */
	UserPaymentDto preCheckPayment(String token, String cardId, int payment);

}
