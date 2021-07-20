package hu.otp.simple.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.simple.common.ErrorMessages;
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

		if (user == null && cardId == null) {
			// TODO
		}

		if (cardId != null) {
			UserBankCard card = userBankCardsRepository.findByCardId(cardId);

			if (card == null) {
				throw new UserException(ErrorMessages.CARD_NOT_FOUND);
			}
			if (card.getUserId().equals(user.getUserId())) {
				return true;
			} else {
				throw new UserException(ErrorMessages.CARD_AND_USER_NOT_MATCH);
			}

		}

		return false;
	}

	@Override
	public boolean hasCardCoverage(int price, String cardId) {

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

	private boolean isTokenValid(List<UserToken> tokens, String actualToken) {
		for (UserToken userToken : tokens) {
			if (userToken.getToken().equals(actualToken)) {
				return true;
			}
		}
		return false;
	}

}
