package hu.otp.simple.ticket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.dtos.ReservationErrorDto;
import hu.otp.simple.common.exceptions.EventException;
import hu.otp.simple.common.exceptions.ReservationException;
import hu.otp.simple.common.exceptions.UserException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	@ExceptionHandler(ReservationException.class)
	public ResponseEntity<ReservationErrorDto> handleExceptionReserve(ReservationException e) {

		log.info("Hiba a foglalásnál! {}", e.getErrorMessage().getSimpleMessage());
		ReservationErrorDto dto = new ReservationErrorDto(e.getErrorMessage().getCode());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
	}

	@ExceptionHandler(EventException.class)
	public ResponseEntity<ReservationErrorDto> handleEventException(EventException e) {

		log.info("Hiba az események lekérdezésénél! {}", e.getErrorMessage().getSimpleMessage());
		ReservationErrorDto dto = new ReservationErrorDto(e.getErrorMessage().getCode());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorMessages> handleUserException(UserException e) {
		log.info("Hiba a felhasználó validálása/fizetése során.: {}", e.getErrorMessage().getSimpleMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getErrorMessage());
	}

}
