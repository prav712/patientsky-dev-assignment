package com.patientsky.dev.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.patientsky.dev.model.Appointment;
import com.patientsky.dev.model.CalendarData;
import com.patientsky.dev.model.TimeSlot;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Service;

@Service
public class TimeAvailabilityService {
	private static final String FILE_PATH = "src/main/resources/calendardata";
	private CalendarData calendarData;
	private final JsonParserService jsonParserService;

	public TimeAvailabilityService(JsonParserService jsonParserService) {
		this.jsonParserService = jsonParserService;
	}

	@PostConstruct
	private void init() {
		calendarData = jsonParserService.jsonToPojo(FILE_PATH);
	}

	public List<TimeSlot> findAvailableTime(List<UUID> calendarIds, int length, Interval period, UUID timeSlotTypeId) {
		final List<Appointment> appointmentsWithInPeriod = calendarData.getAppointments()
				.stream()
				.filter(appointment -> areDatesWithInPeriod(appointment.getStart(), appointment.getEnd(), period))
				.filter(appointment -> timeSlotTypeId == null || appointment.getTime_slot_type_id().equals(timeSlotTypeId))
				.collect(Collectors.toList());
		return calendarIds.stream()
				.map(calendarId -> calendarData.getTimeslots()
						.stream()
						.filter(timeSlot -> timeSlot.getCalendar_id().equals(calendarId)
								&& areDatesWithInPeriod(timeSlot.getStart(), timeSlot.getEnd(), period)
								&& hasMatchingTimSlotType(length, timeSlotTypeId, timeSlot)
								&& hasNoAppointmentWithInTimeSlot(calendarId, timeSlot, appointmentsWithInPeriod))
						.collect(Collectors.toList()))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	private boolean areDatesWithInPeriod(DateTime start, DateTime end, Interval period) {
		return (start.equals(period.getStart()) || start.isAfter(period.getStart()))
				&& (end.equals(period.getEnd()) || end.isBefore(period.getEnd()));
	}

	private boolean hasNoAppointmentWithInTimeSlot(UUID calendarId, TimeSlot timeSlot, List<Appointment> appointmentsWithInPeriod) {
		return appointmentsWithInPeriod
				.stream()
				.noneMatch(appointment -> calendarId.equals(appointment.getCalendar_id()) &&
						new Interval(appointment.getStart(), appointment.getEnd())
								.overlap(new Interval(timeSlot.getStart(), timeSlot.getEnd())) != null);
	}

	private boolean hasMatchingTimSlotType(int length, UUID timeSlotTypeId, TimeSlot timeSlot) {
		return calendarData.getTimeslottypes()
				.stream()
				.anyMatch(timeSlotType -> Optional.ofNullable(timeSlotTypeId)
						.map(slotTypeId -> slotTypeId.equals(timeSlotType.getId()) && timeSlotType.getId().equals(timeSlot.getType_id()) && timeSlotType.getSlot_size() == length)
						.orElseGet(() -> timeSlotType.getId().equals(timeSlot.getType_id()) && timeSlotType.getSlot_size() == length));
	}

	@PreDestroy
	public void preDestroy() {
		calendarData = null;
	}
}
