package com.patientsky.dev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

 import com.patientsky.dev.model.TimeSlot;
import com.patientsky.dev.service.TimeAvailabilityService;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//todo Needs so many more test cases to cover all the scenarios
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan
class TimeAvailabilityServiceTest {
	private final static DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
	@Autowired
	private TimeAvailabilityService timeAvailabilityService;

	@Test
	public void shouldReturnTwoAvailableTimeSlots() {
		DateTime from = FORMATTER.parseDateTime("2019-04-23T18:15:00");
		DateTime to = FORMATTER.parseDateTime("2019-04-23T19:30:00");

		Interval interval
				= new Interval(from, to);
		final UUID uuid = UUID.fromString("48cadf26-975e-11e5-b9c2-c8e0eb18c1e9");

		final List<TimeSlot> availableTime = timeAvailabilityService.findAvailableTime(Collections.singletonList(uuid), 15, interval, null);

		assertEquals(2, availableTime.size());
	}

	@Test
	public void shouldReturnTwoAvailableTimeSlotsForTypeUuid() {
		DateTime from = FORMATTER.parseDateTime("2019-04-23T18:15:00");
		DateTime to = FORMATTER.parseDateTime("2019-04-23T19:30:00");

		Interval interval
				= new Interval(from, to);

		final UUID uuid = UUID.fromString("48cadf26-975e-11e5-b9c2-c8e0eb18c1e9");
		final UUID typeUuid = UUID.fromString("452935de-975e-11e5-ae1a-c8e0eb18c1e9");

		final List<TimeSlot> availableTime = timeAvailabilityService.findAvailableTime(Collections.singletonList(uuid), 15, interval, typeUuid);

		assertEquals(2, availableTime.size());
	}
}