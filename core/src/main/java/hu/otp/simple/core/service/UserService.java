package hu.otp.simple.core.service;

import hu.otp.simple.core.domain.User;

public interface UserService {

	User validateUserByUserToken(String token);

	boolean isUserCardOwner(User user, String cardId);

	boolean hasCardCoverage(int price, String cardId);

}
