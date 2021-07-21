package hu.otp.simple.ticket.clients;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.dtos.ReserveDto;

@Service
public class PartnerClient {
	@Value("${restclient.url.partner}")
	private String partnerUrl;
	private static Random rnd = new Random();

	public List<Event> queryEvents() {

		String url = partnerUrl + "/getEvents";

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

		String url = partnerUrl + "/getEvent";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		// TODO: errorhandling ide!
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("id", id);
		HttpEntity<EventInfo> response = restTemplate.getForEntity(builder.build().encode().toUri(), EventInfo.class);

		return response.getBody();
	}

	public Event queryEventDescription(long id) {

		String url = partnerUrl + "/getEventDescription";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("id", id);
		HttpEntity<Event> response = restTemplate.getForEntity(builder.build().encode().toUri(), Event.class);

		return response.getBody();
	}

	public ReserveDto reserve(long eventId, long seatId) {

		String url = partnerUrl + "/reserve";

		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("EventId", eventId).queryParam("SeatId", seatId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("EventId", eventId);
		map.add("SeatId", seatId);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

		HttpEntity<ReserveDto> response = restTemplate.postForEntity(builder.build().encode().toUri(), request, ReserveDto.class);

		return response.getBody();
	}

	public boolean isAlive() {

		int hb = rnd.nextInt();
		String url = partnerUrl + "/heartbeat";
		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("heartbeat", hb).queryParam("client",
				"OTP-Simple Ticket");
		HttpEntity<Integer> response = restTemplate.getForEntity(builder.build().encode().toUri(), Integer.class);

		Integer result = response.getBody();
		if (result != null && result == hb + 1) {
			return true;
		}
		return false;
	}

}
