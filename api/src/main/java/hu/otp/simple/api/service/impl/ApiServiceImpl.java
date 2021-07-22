package hu.otp.simple.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.otp.simple.api.client.CoreClient;
import hu.otp.simple.api.client.TicketClient;
import hu.otp.simple.api.service.ApiService;
import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.dtos.ReserveDto;
import hu.otp.simple.common.dtos.UserValidationDto;
import hu.otp.simple.common.exceptions.ServiceException;
import hu.otp.simple.common.exceptions.UserException;

/**
 * Default implementation of api service.
 * 
 * @author vforjan
 *
 */
@Service
public class ApiServiceImpl implements ApiService {

	private static Logger log = LoggerFactory.getLogger(ApiServiceImpl.class);

	@Autowired
	private CoreClient coreClient;

	@Autowired
	private TicketClient ticketClient;

	@Override
	public UserValidationDto validateUser(String token) {

		log.info("User token ellenőrzése.");
		UserValidationDto dto = coreClient.validateUserToken(token);

		if (dto == null) {
			log.info("A szolgáltatás nem elérhető.");
			ErrorMessages message = ErrorMessages.SERVICE_UNREACHABLE;
			throw new ServiceException(message);
		}
		if (!dto.isSuccess()) {

			ErrorMessages message = dto.getOptionalError();
			throw new UserException(message);
		}

		log.info("Felhasználó token érvényesítve!");
		return dto;
	}

	@Override
	public ReserveDto payAttempt(long eventId, long seatId, long cardId, String token) {
		log.info("Helyfoglalás és fizetés kezdeményezése. EventId ={}, CardId={}", eventId, cardId);

		final String cardIdString = resolveCardId(cardId);
		return ticketClient.reserveAndPay(eventId, seatId, cardIdString, token);

	}

	/**
	 * Card id resolver.
	 * 
	 * @param cardId the card id in number format
	 * @return the inner <c ode>String</code> representation fo card Id.
	 */
	private String resolveCardId(long cardId) {
		String idString = Long.toString(cardId);
		StringBuilder builder = new StringBuilder();
		builder.append("C");
		while (builder.length() + idString.length() < 5) {
			builder.append("0");

		}
		builder.append(cardId);
		return builder.toString();

	}

}
