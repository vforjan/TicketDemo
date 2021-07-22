package hu.otp.simple.ticket.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.exceptions.EventException;

@Component
public class RestTemplateEventResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

		return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
				|| httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {

		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
			throw new EventException(ErrorMessages.EVENT_NOT_EXIST);
		} else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
			throw new EventException(ErrorMessages.EVENT_NOT_EXIST);
		}
	}

}