package hu.otp.simple.ticket.clients;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import hu.otp.simple.common.dtos.UserPaymentDto;
import hu.otp.simple.common.dtos.UserValidationDto;

/**
 * Client for communicating the core module's microservice
 * 
 * @author vforjan
 *
 */
@Service
public class CoreClient {
	@Value("${restclient.url.core}")
	private String coreUrl;
	private static Random rnd = new Random();

	/**
	 * Core call to validate user by its provided token.
	 * 
	 * @param token the user token
	 * @return <code>UserValidationDto</code> result
	 */
	public UserValidationDto validateUserToken(String token) {

		String url = coreUrl + "/validate-token";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("token", token);

		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<UserValidationDto> response = restTemplate.getForEntity(builder.build().encode().toUri(), UserValidationDto.class);

		return response.getBody();

	}

	/**
	 * Validate user payment
	 * 
	 * @param token the suer token
	 * @param cardId the card id
	 * @param payment the payment
	 * @return the UserPaymentDto represents the results of the validation
	 */
	public UserPaymentDto validateUserPayment(String token, String cardId, int payment) {

		String url = coreUrl + "/validate-payment";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("token", token).queryParam("cardId", cardId)
				.queryParam("payment", payment);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<UserPaymentDto> response = restTemplate.getForEntity(builder.build().encode().toUri(), UserPaymentDto.class);
		return response.getBody();

	}

	public boolean isAlive() {
		int hb = rnd.nextInt();
		String url = coreUrl + "/heartbeat";
		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("heartbeat", hb).queryParam("client",
				"OTP-Simple Ticket");
		HttpEntity<Integer> response = restTemplate.getForEntity(builder.build().encode().toUri(), Integer.class);

		Integer result = response.getBody();
		if (result != null && result == hb + 2) {
			return true;
		}
		return false;
	}

}