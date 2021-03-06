package hu.otp.simple.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import hu.otp.simple.api.controller.RestTemplateUserResponseErrorHandler;
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

	/**
	 * Core call to validate user by its provided token.
	 * 
	 * @param token the user token
	 * @return <code>UserValidationDto</code> result
	 */
	public UserValidationDto validateUserToken(String token) {

		String url = coreUrl + "/validate-token";

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestTemplateUserResponseErrorHandler());

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("token", token);

		HttpEntity<UserValidationDto> response = restTemplate.getForEntity(builder.build().encode().toUri(), UserValidationDto.class);

		return response.getBody();

	}

}
