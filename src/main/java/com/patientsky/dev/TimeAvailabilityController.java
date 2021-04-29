package com.patientsky.dev;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.patientsky.dev.model.TimeSlot;
import com.patientsky.dev.service.TimeAvailabilityService;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeAvailabilityController {
	private TimeAvailabilityService timeAvailabilityService;

	public TimeAvailabilityController(TimeAvailabilityService timeAvailabilityService) {
		this.timeAvailabilityService = timeAvailabilityService;
	}

	@GetMapping("/availabletimeslots")
	public List<TimeSlot> getTimeSlots(@RequestParam List<String> calendarIds, @RequestParam int length, @RequestParam String start, @RequestParam String end, @RequestParam(required = false) String timeSlotTypeId) {
		final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
		dateTimeFormatter.withZone(DateTimeZone.UTC);
		DateTime from = dateTimeFormatter.parseDateTime(start);
		DateTime to = dateTimeFormatter.parseDateTime(end);
		final List<UUID> calendarUuids = calendarIds.stream().map(UUID::fromString).collect(Collectors.toList());
		final UUID timeSlotTypeUuid = Strings.isNotBlank(timeSlotTypeId) ? UUID.fromString(timeSlotTypeId) : null;

		return timeAvailabilityService.findAvailableTime(calendarUuids, length, new Interval(from, to), timeSlotTypeUuid);
	}
}
