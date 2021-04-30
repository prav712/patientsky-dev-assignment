package com.patientsky.dev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarData {
	private List<Appointment> appointments;
	private List<TimeSlot> timeslots;
	private List<TimeSlotType> timeslottypes;
}
