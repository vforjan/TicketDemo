package hu.otp.simple.service;

import hu.otp.simple.domain.User;

public interface UserService {

	User validateUserByUserToken(String token);

	boolean isUserCardOwner(User user, String cardId);

	boolean hasCardCoverage(int price, String cardId);

}
