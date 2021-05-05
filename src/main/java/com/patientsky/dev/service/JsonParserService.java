package com.patientsky.dev.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.patientsky.dev.model.CalendarData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.patientsky.dev.util.TimeAvailabilityUtil.EUROPE_OSLO;
import static java.nio.file.Files.walk;
import static java.util.TimeZone.getTimeZone;

@Service
public class JsonParserService {
	private static final Logger logger = LoggerFactory.getLogger(JsonParserService.class.getName());

	public Map<UUID, CalendarData> jsonToPojo(String folderPath) {
		Map<UUID, CalendarData> calendarDataByCalendarId = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JodaModule());
			objectMapper.setTimeZone(getTimeZone(EUROPE_OSLO));
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			try (Stream<Path> walk = walk(Paths.get(folderPath))) {
				walk
						.filter(Files::isRegularFile)
						.map(Path::toFile)
						.forEach(file -> {
							try {
								Optional.ofNullable(objectMapper.readValue(file, CalendarData.class))
										.ifPresent(calendarData -> Optional.ofNullable(calendarData.getAppointments())
												.ifPresent(appointments -> calendarDataByCalendarId.put(appointments.get(0).getCalendar_id(), calendarData)));
							} catch (IOException e) {
								logger.error(e.getMessage());
							}
						});
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
			return calendarDataByCalendarId;
		}
		return calendarDataByCalendarId;
	}
}
