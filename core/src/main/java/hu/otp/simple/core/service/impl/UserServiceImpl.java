package hu.otp.simple.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.simple.core.domain.ErrorMessages;
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
	public User validateUserByUserToken(String token) {
		User user1 = userRepository.findByUserId(1000);
		System.out.println(user1.getEmail());

		log.debug(token);
		if (token.isEmpty()) {
			log.error("Helytelen UserToken");
			return null;
		}

		ValidationDto dto = ValidationUtils.getEncodedDataFromToken(token);
		System.out.println(dto);

		if (dto.getUserId() < 0) {
			log.error("Helytelen UserId");
			return null;
		}

		User user = userRepository.findByUserId(dto.getUserId());
		if (user == null) {
			log.error("Nem található felhasználó.");
			return null;
		}

		List<UserToken> tokens = userTokenRepository.findByUserId(dto.getUserId());
		if (!isTokenValid(tokens, token)) {
			log.error("Lejárt token");
			return null;
		}

		UserDevice userDevice = userDeviceRepository.findByUserIdAndDeviceHash(dto.getUserId(), dto.getDeviceHash());

		if (userDevice == null) {
			log.info("Ismeretlen készülék");
			return null;
		}

		if (user.getEmail().equals(dto.getEmail())) {
			return user;
		}

		return null;
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
