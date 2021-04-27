package com.patientsky.dev.service;

import static java.nio.file.Files.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.patientsky.dev.model.CalendarData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JsonParserService {
	private static final Logger logger = LoggerFactory.getLogger(JsonParserService.class.getName());

	public CalendarData jsonToPojo(String folderPath) {
		CalendarData rv = CalendarData.builder().appointments(new ArrayList<>())
				.timeslots(new ArrayList<>())
				.timeslottypes(new ArrayList<>())
				.build();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JodaModule());
			objectMapper.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			walk(Paths.get(folderPath))
					.filter(Files::isRegularFile)
					.map(Path::toFile)
					.forEach(file -> {
						try {
							CalendarData calendarData = objectMapper.readValue(file, CalendarData.class);
							rv.getAppointments().addAll(calendarData.getAppointments());
							rv.getTimeslots().addAll(calendarData.getTimeslots());
							rv.getTimeslottypes().addAll(calendarData.getTimeslottypes());
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					});

		} catch (IOException e) {
			logger.error(e.getMessage());
			return rv;
		}
		rv.setTimeslottypes(rv.getTimeslottypes().stream().distinct().collect(Collectors.toList()));
		return rv;
	}
}
