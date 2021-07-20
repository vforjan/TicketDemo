package hu.otp.simple.partner.Controller;

import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hu.otp.simple.partner.ReservationException;
import hu.otp.simple.partner.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleExceptionNotFound(ResourceNotFoundException e) {
		// log exception
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A kért esemény nem található!");
	}

	@ExceptionHandler(ParseException.class)
	public ResponseEntity<String> handleExceptionParse(ParseException e) {
		// log exception
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A kért esemény nem található!");
	}

	@ExceptionHandler(ReservationException.class)
	public ResponseEntity<ReservationErrorDto> handleExceptionReserve(ReservationException e) {
		// log exception
		ReservationErrorDto dto = new ReservationErrorDto(e.getErrorCode());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
	}
}
