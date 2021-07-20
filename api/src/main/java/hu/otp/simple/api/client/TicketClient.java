package hu.otp.simple.api.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;

@Service
public class TicketClient {
	@Value("${restclient.url.ticket}")
	private String ticketUrl;

	public List<Event> queryEvents() {

		String url = ticketUrl + "/getEvents";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		HttpEntity<Event[]> response = restTemplate.getForEntity(builder.build().encode().toUri(), Event[].class);

		Event[] responseArray = response.getBody();
		if (responseArray != null && responseArray.length > 0) {

			return Arrays.asList(responseArray);
		}
		return Collections.emptyList();
	}

	public EventInfo queryEvent(long id) {

		String url = ticketUrl + "/getEvent";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("id", id);
		HttpEntity<EventInfo> response = restTemplate.getForEntity(builder.build().encode().toUri(), EventInfo.class);

		return response.getBody();
	}
}
