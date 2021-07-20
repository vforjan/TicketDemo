package hu.otp.simple.partner.service.impl;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.partner.ReservationException;
import hu.otp.simple.partner.domain.Reserve;
import hu.otp.simple.partner.domain.SeatInfo;
import hu.otp.simple.partner.service.ReservationService;
import hu.otp.simple.partner.utils.ResourceHandlingUtils;

@Service
public class ReservationServiceImpl implements ReservationService {
	private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Override
	public Reserve reserve(long eventId, long seatId) {

		final String id = "S" + seatId;
		List<SeatInfo> infos = null;

		try {
			infos = ResourceHandlingUtils.readReservationInfo(eventId);
		} catch (Exception e) {
			log.info("Sikertelen hely foglalás EventId = {}, SeatId = {}", eventId, seatId);
			throw new ReservationException(ErrorMessages.EVENT_NOT_EXIST);
		}

		SeatInfo info = infos.stream().filter(s -> id.equals(s.getId())).findFirst().orElse(null);
		if (info == null) {
			throw new ReservationException(ErrorMessages.SEAT_NOT_EXIST);
		}

		if (info.isReserved()) {
			throw new ReservationException(ErrorMessages.RESERVED_SEAT);
		}

		return new Reserve(new Random().nextLong(), true);
	}

}
