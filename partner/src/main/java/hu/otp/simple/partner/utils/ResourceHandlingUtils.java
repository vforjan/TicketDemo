package hu.otp.simple.partner.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.otp.simple.common.ErrorMessages;
import hu.otp.simple.common.domain.Event;
import hu.otp.simple.common.domain.EventInfo;
import hu.otp.simple.common.domain.EventReserve;
import hu.otp.simple.common.domain.EventWrapper;
import hu.otp.simple.common.exceptions.ReservationException;
import hu.otp.simple.common.exceptions.ResourceNotFoundException;

public class ResourceHandlingUtils {
	private static Logger log = LoggerFactory.getLogger(ResourceHandlingUtils.class);

	@Value("classpath:data/getEvents.json")
	public static Resource eventsResource;

	public static List<Event> getContentOfEvents() {
		return getListOfEvents();
	}

	public static Event getEventById(long id) {
		List<Event> events = getListOfEvents();
		return events.stream().filter(e -> id == e.getEventId()).findFirst().orElse(null);
	}

	public static EventInfo getEventInfoById(long eventId) {
		ObjectMapper objectMapper = new ObjectMapper();
		EventReserve reserve = null;
		String jsonInput = getContentOfEventByIdFromFileResource(eventId);

		try {
			reserve = objectMapper.readValue(jsonInput, EventReserve.class);
		} catch (IOException e) {
			return null;
		}
		return reserve.getData();

	}

	private static String getContentFromFileResource(Resource resource) {
		String content = null;
		try (Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8")) {
			content = FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new ResourceNotFoundException(resource.getFilename(), ErrorMessages.RESOURCE_NOT_FOUND);
		}

		return content;
	}

	private static String getContentOfEventByIdFromFileResource(long id) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		String content = null;
		try {
			Resource resource = resourceLoader.getResource("classpath:/data/getEvent" + id + ".json");
			content = getContentFromFileResource(resource);
		} catch (ResourceNotFoundException e) {
			throw new ReservationException(ErrorMessages.EVENT_NOT_EXIST);
		}
		return content;
	}

	private static List<Event> getListOfEvents() {
		ObjectMapper objectMapper = new ObjectMapper();
		EventWrapper wrapper = null;
		// TODO: use properties
		String resourceString = "data/getEvents.json";

		String jsonInput = getContentFromFileResource(new ClassPathResource(resourceString));

		try {
			wrapper = objectMapper.readValue(jsonInput, EventWrapper.class);
		} catch (IOException e) {
			log.error("A kért erőforrás nem található.{} ", resourceString);
			throw new ResourceNotFoundException(resourceString, ErrorMessages.RESOURCE_NOT_FOUND);
		}

		if (wrapper == null) {
			log.error("A kért erőforrás nem található.{} ", resourceString);
			return Collections.emptyList();
		}

		return wrapper.getData();
	}
}
