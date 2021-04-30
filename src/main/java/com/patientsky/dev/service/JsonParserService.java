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
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.patientsky.dev.util.TimeAvailabilityUtil.EUROPE_OSLO;
import static java.nio.file.Files.walk;
import static java.util.TimeZone.getTimeZone;

@Service
public class JsonParserService {
	private static final Logger logger = LoggerFactory.getLogger(JsonParserService.class.getName());

	public CalendarData jsonToPojo(String folderPath) {
		CalendarData calendarDataContainer = CalendarData.builder().appointments(new ArrayList<>())
				.timeslots(new ArrayList<>())
				.timeslottypes(new ArrayList<>())
				.build();
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
								CalendarData calendarData = objectMapper.readValue(file, CalendarData.class);
								calendarDataContainer.getAppointments().addAll(calendarData.getAppointments());
								calendarDataContainer.getTimeslots().addAll(calendarData.getTimeslots());
								calendarDataContainer.getTimeslottypes().addAll(calendarData.getTimeslottypes());
							} catch (IOException e) {
								logger.error(e.getMessage());
							}
						});
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
			return calendarDataContainer;
		}
		calendarDataContainer.setTimeslottypes(calendarDataContainer.getTimeslottypes().stream().distinct().collect(Collectors.toList()));
		return calendarDataContainer;
	}
}
