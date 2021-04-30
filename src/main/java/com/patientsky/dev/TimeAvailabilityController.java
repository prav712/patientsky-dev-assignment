package com.patientsky.dev;

import com.patientsky.dev.model.TimeSlot;
import com.patientsky.dev.service.TimeAvailabilityService;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.patientsky.dev.util.TimeAvailabilityUtil.FORMATTER;

@RestController
public class TimeAvailabilityController {
	private TimeAvailabilityService timeAvailabilityService;

	public TimeAvailabilityController(TimeAvailabilityService timeAvailabilityService) {
		this.timeAvailabilityService = timeAvailabilityService;
	}

	@GetMapping("/availabletimeslots")
	public List<TimeSlot> getTimeSlots(@RequestParam List<String> calendarIds, @RequestParam int length, @RequestParam String start, @RequestParam String end, @RequestParam(required = false) String timeSlotTypeId) {
		DateTime from = FORMATTER.parseDateTime(start);
		DateTime to = FORMATTER.parseDateTime(end);

		final List<UUID> calendarUUIDs = calendarIds.stream().map(UUID::fromString).collect(Collectors.toList());
		final UUID timeSlotTypeUuid = Strings.isNotBlank(timeSlotTypeId) ? UUID.fromString(timeSlotTypeId) : null;

		return timeAvailabilityService.findAvailableTime(calendarUUIDs, length, new Interval(from, to), timeSlotTypeUuid);
	}
}
