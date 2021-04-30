package com.patientsky.dev.service;

import com.patientsky.dev.model.TimeSlot;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.patientsky.dev.util.TimeAvailabilityUtil.FORMATTER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

//todo Needs so many more test cases to cover all the scenarios
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan
class TimeAvailabilityServiceTest {
	@Autowired
	private TimeAvailabilityService timeAvailabilityService;

	@Test
	void shouldReturnTwoAvailableTimeSlots() {
		DateTime from = FORMATTER.parseDateTime("2019-04-23T18:15:00");
		DateTime to = FORMATTER.parseDateTime("2019-04-23T19:30:00");

		Interval interval
				= new Interval(from, to);
		final UUID uuid = UUID.fromString("48cadf26-975e-11e5-b9c2-c8e0eb18c1e9");

		final List<TimeSlot> availableTimeSlots = timeAvailabilityService.findAvailableTime(Collections.singletonList(uuid), 15, interval, null);

		assertThat(availableTimeSlots, hasSize(2));
	}

	@Test
	void shouldReturnTwoAvailableTimeSlotsForTypeUuid() {
		DateTime from = FORMATTER.parseDateTime("2019-04-23T18:15:00");
		DateTime to = FORMATTER.parseDateTime("2019-04-23T19:30:00");

		Interval interval
				= new Interval(from, to);

		final UUID uuid = UUID.fromString("48cadf26-975e-11e5-b9c2-c8e0eb18c1e9");
		final UUID typeUuid = UUID.fromString("452935de-975e-11e5-ae1a-c8e0eb18c1e9");

		final List<TimeSlot> availableTimeSlots = timeAvailabilityService.findAvailableTime(Collections.singletonList(uuid), 15, interval, typeUuid);

		assertThat(availableTimeSlots, hasSize(2));
	}
}