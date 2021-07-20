package hu.otp.simple.common.validation;

import java.util.List;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.domain.SeatInfo;
import hu.otp.simple.common.exceptions.ReservationException;

public class ValidationUtils {

	public static boolean setValidation(long seatId, List<SeatInfo> seats) {

		final String id = "S" + seatId;

		SeatInfo info = seats.stream().filter(s -> id.equals(s.getId())).findFirst().orElse(null);
		if (info == null) {
			throw new ReservationException(ErrorMessages.SEAT_NOT_EXIST);
		}

		if (info.isReserved()) {
			throw new ReservationException(ErrorMessages.RESERVED_SEAT);
		}
	}

}
