package hu.otp.simple.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.dtos.ReservationErrorDto;
import hu.otp.simple.common.exceptions.ReservationException;
import hu.otp.simple.common.exceptions.ResourceNotFoundException;
import hu.otp.simple.common.exceptions.UserException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleExceptionNotFound(ResourceNotFoundException e) {
		log.info("Hiba az események lekérdezésénél.");

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A kért esemény nem található! - Fájl nem található");
	}

	@ExceptionHandler(ParseException.class)
	public ResponseEntity<String> handleExceptionParse(ParseException e) {
		log.info("Hiba az események lekérdezésénél.");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A kért esemény nem található! - Parser hiba");
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorMessages> handleUserException(UserException e) {
		log.info("Hiba a felhasználó validálása során.: {}", e.getErrorMessage().getSimpleMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getErrorMessage());
	}

	@ExceptionHandler(ReservationException.class)
	public ResponseEntity<ReservationErrorDto> handleExceptionReserve(ReservationException e) {

		log.info("Hiba a foglalásnál! {}", e.getErrorMessage().getSimpleMessage());
		ReservationErrorDto dto = new ReservationErrorDto(e.getErrorMessage().getCode());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
	}
}
