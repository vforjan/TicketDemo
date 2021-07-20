package hu.otp.simple.partner.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.otp.simple.partner.ResourceNotFoundException;
import hu.otp.simple.partner.domain.EventReserve;
import hu.otp.simple.partner.domain.SeatInfo;

public class ResourceHandlingUtils {

	@Value("classpath:data/getEvents.json")
	private static Resource eventsResource;

	public static String getContentOfEventsFromFileResource() {
		return getContentFromFileResource(eventsResource);

	}

	public static String getContentOfEventByIdFromFileResource(long id) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("classpath:/data/getEvent" + id + ".json");
		return getContentFromFileResource(resource);

	}

	public static List<SeatInfo> readReservationInfo(long eventId) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		EventReserve reserve = null;
		String jsonInput = getContentOfEventByIdFromFileResource(eventId);

		try {
			reserve = objectMapper.readValue(jsonInput, EventReserve.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reserve.getData().getSeats();

	}

	private static String getContentFromFileResource(Resource resource) {
		String content = null;
		try (Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8")) {
			content = FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new ResourceNotFoundException(resource.getFilename());
		}

		return content;
	}

}
