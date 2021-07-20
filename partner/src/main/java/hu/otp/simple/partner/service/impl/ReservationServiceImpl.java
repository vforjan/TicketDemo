package hu.otp.simple.partner.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import hu.otp.simple.partner.ReservationException;
import hu.otp.simple.partner.domain.Reserve;
import hu.otp.simple.partner.domain.SeatInfo;
import hu.otp.simple.partner.service.ReservationService;
import hu.otp.simple.partner.utils.ResourceHandlingUtils;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Override
	public Reserve reserve(long eventId, long seatId) {

		final String id = "S" + seatId;
		List<SeatInfo> infos = null;
		try {
			infos = ResourceHandlingUtils.readReservationInfo(eventId);
		} catch (Exception e) {
			throw new ReservationException(90001);
		}

		SeatInfo info = infos.stream().filter(s -> id.equals(s.getId())).findFirst().orElse(null);
		if (info == null) {
			throw new ReservationException(90002);
		}

		if (info.isReserved()) {
			throw new ReservationException(90010);

		}

		return new Reserve(new Random().nextLong(), true);
	}

}
