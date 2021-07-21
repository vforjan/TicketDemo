package hu.otp.simple.partner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.dtos.ReservationErrorDto;
import hu.otp.simple.common.exceptions.EventException;
import hu.otp.simple.common.exceptions.ReservationException;
import hu.otp.simple.common.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleExceptionNotFound(ResourceNotFoundException e) {
		log.info("Hiba az események lekérdezésénél.");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A kért esemény nem található! - Fájl nem található");
	}

	@ExceptionHandler(ParseException.class)
	public ResponseEntity<String> handleExceptionParse(ParseException e) {
		log.info("Hiba az események lekérdezésénél.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A kért esemény nem található! - Parser hiba");
	}

	@ExceptionHandler(ReservationException.class)
	public ResponseEntity<ReservationErrorDto> handleExceptionReserve(ReservationException e) {

		log.info("Hiba a foglalásnál! {}", e.getErrorMessage().getSimpleMessage());
		ReservationErrorDto dto = new ReservationErrorDto(e.getErrorMessage().getCode());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
	}

	@ExceptionHandler(EventException.class)
	public ResponseEntity<ErrorMessages> handleEventException(ReservationException e) {

		log.info("Hiba az események lekérdezésénél! {}", e.getErrorMessage().getSimpleMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getErrorMessage());
	}
}
