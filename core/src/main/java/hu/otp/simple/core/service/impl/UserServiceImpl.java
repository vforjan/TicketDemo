package hu.otp.simple.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.dtos.UserPaymentDto;
import hu.otp.simple.common.dtos.UserValidationDto;
import hu.otp.simple.common.exceptions.UserException;
import hu.otp.simple.core.domain.User;
import hu.otp.simple.core.domain.UserBankCard;
import hu.otp.simple.core.domain.UserDevice;
import hu.otp.simple.core.domain.UserToken;
import hu.otp.simple.core.domain.ValidationDto;
import hu.otp.simple.core.repository.UserBankCardsRepository;
import hu.otp.simple.core.repository.UserDeviceRepository;
import hu.otp.simple.core.repository.UserRepository;
import hu.otp.simple.core.repository.UserTokenRepository;
import hu.otp.simple.core.service.UserService;

/**
 * Implementation of UserService interface.
 * 
 * Service for user and user's card validation.
 * 
 * @author vforjan
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserBankCardsRepository userBankCardsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDeviceRepository userDeviceRepository;

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Override
	public UserValidationDto validateUserByUserToken(String token) {
		UserValidationDto userDto = new UserValidationDto();
		userDto.setToken(token);
		log.info("Validate user token.");
		if (token.isEmpty()) {
			log.warn("Helytelen UserToken");
			userDto.setOptionalError(ErrorMessages.NOT_FOUND_TOKEN);
			userDto.setSuccess(false);
			return userDto;
		}

		ValidationDto validationDto = ValidationUtils.getEncodedDataFromToken(token);

		if (validationDto.getUserId() < 0) {
			log.warn("Helytelen UserId");
			userDto.setOptionalError(ErrorMessages.USER_ID_NOT_FOUND);
			userDto.setSuccess(false);
			return userDto;
		}

		userDto.setUserId(validationDto.getUserId());
		User user = userRepository.findByUserId(validationDto.getUserId());
		if (user == null) {
			log.warn("Nem található felhasználó.");
			userDto.setOptionalError(ErrorMessages.USER_NOT_FOUND);
			userDto.setSuccess(false);
			return userDto;
		}

		List<UserToken> tokens = userTokenRepository.findByUserId(validationDto.getUserId());
		if (!isTokenValid(tokens, token)) {
			log.warn("Lejárt token. UserId = {}", validationDto.getUserId());
			userDto.setOptionalError(ErrorMessages.TOKEN_EXPIRED);
			userDto.setSuccess(false);
			return userDto;
		}

		UserDevice userDevice = userDeviceRepository.findByUserIdAndDeviceHash(validationDto.getUserId(), validationDto.getDeviceHash());

		if (userDevice == null) {
			log.warn("Ismeretlen készülék, deviceHash = {}", validationDto.getDeviceHash());
			log.warn("Lejárt token. UserId = {}", validationDto.getUserId());
			userDto.setOptionalError(ErrorMessages.DEVICE_UNKNOWN);
			userDto.setSuccess(false);
			return userDto;
		}

		if (user.getEmail().equals(validationDto.getEmail())) {
			userDto.setSuccess(true);
			return userDto;
		} else {
			userDto.setOptionalError(ErrorMessages.EMAIL_MISMATCH);
			userDto.setSuccess(false);
			return userDto;
		}
	}

	@Override
	public boolean isUserCardOwner(User user, String cardId) {

		return checkIsUserCardOwner(user, cardId);
	}

	@Override
	public UserPaymentDto preCheckPayment(String token, String cardId, int payment) {

		UserPaymentDto dto = new UserPaymentDto();
		dto.setToken(token);
		try {
			User validatedUser = getValidatedUserFromToken(token);

			if (validatedUser == null) {
				log.warn("Nem található felhasználó.");
				dto.setSuccess(false);
				dto.setOptionalError(ErrorMessages.USER_NOT_FOUND);
				return dto;
			}

			if (checkIsUserCardOwner(validatedUser, cardId)) {
				log.warn("A felhasználó bankártyája hitelesítve");
			} else {
				log.warn("A felhasználó nem rendelkezik ilyen kártyával. UserId = {}, CardId = {}", validatedUser.getUserId(), cardId);
				dto.setSuccess(false);
				dto.setOptionalError(ErrorMessages.CARD_AND_USER_NOT_MATCH);
				return dto;
			}
			if (!checkCardCoverage(payment, cardId)) {
				log.info("Nincs elég fedezet a kártyán. CardId = {}", cardId);
				dto.setSuccess(false);
				dto.setOptionalError(ErrorMessages.NOT_ENOUGH_COVERAGE);
				return dto;
			}

			dto.setSuccess(true);
			return dto;

		} catch (UserException e) {
			log.info("Hiba a fedezet validálása közben. cardId = {}, hibaüzenet = {}", cardId, e.getErrorMessage().getSimpleMessage());
			dto.setOptionalError(e.getErrorMessage());
			dto.setSuccess(false);
			return dto;
		}

	}

	@Override
	public boolean hasCardCoverage(int price, String cardId) {
		checkCardCoverage(price, cardId);

		return false;
	}

	/**
	 * Check the actual tokens against the user token to check the actul tokens validation.
	 * 
	 * @param tokens the tokens
	 * @param actualToken the actual token
	 * @return <code>true</code> if the actual token is valid<code>false</code>
	 */
	private boolean isTokenValid(List<UserToken> tokens, String actualToken) {
		for (UserToken userToken : tokens) {
			if (userToken.getToken().equals(actualToken)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if a card belongs to an actual user.
	 * 
	 * @param user the user
	 * @param cardId the card ID
	 * @return <code>true</code> if the card belongs to the actual user, otherwise <code>false</code>
	 * @throws UserException
	 */
	private boolean checkIsUserCardOwner(User user, String cardId) throws UserException {

		Assert.notNull(user, "User can't be null");
		Assert.notNull(user, "CardId can't be null");

		UserBankCard card = userBankCardsRepository.findByCardId(cardId);

		if (card == null) {
			log.warn("A kártya nem található.");
			throw new UserException(ErrorMessages.CARD_NOT_FOUND);
		}
		if (card.getUserId().equals(user.getUserId())) {
			log.info("Felhasználói kártya ellenőrizve.");
			return true;
		} else {
			log.warn("A kártya nem ehhez a felhasználóhoz tartozik. UserId = {}, cardId = {} ", user.getUserId(), card.getCardId());
			return false;
		}

	}

	/**
	 * Check is a user card has coverage for a transaction.
	 * 
	 * @param price the needeable amount of coverage
	 * @param cardId the card id
	 * @return <code>true</code> if the card has enough amount of coverage otherwise <code>false</code>
	 */
	private boolean checkCardCoverage(int price, String cardId) {

		if (cardId != null) {
			UserBankCard card = userBankCardsRepository.findByCardId(cardId);
			if (card == null) {
				throw new UserException(ErrorMessages.CARD_NOT_FOUND);
			}
			if (card.getAmount() == null || card.getAmount() < price) {
				return false;
			} else {
				return true;
			}

		}
		return false;
	}

	/**
	 * Get validated user from a user token.
	 * 
	 * @param token the user token
	 * @return the validated user entity
	 */
	private User getValidatedUserFromToken(String token) {
		UserValidationDto userValidationDto = validateUserByUserToken(token);
		if (!userValidationDto.isSuccess()) {
			log.info("Sikertelen validálás.");
			throw new UserException(userValidationDto.getOptionalError());
		}
		User user = userRepository.findByUserId(userValidationDto.getUserId());

		return user;

	}

}
