package com.patientsky.dev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.UUID;

import static com.patientsky.dev.util.TimeAvailabilityUtil.EUROPE_OSLO;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlot {
	private UUID id; // Id of time slot
	private UUID calendar_id; // Id of the calendar
	private UUID type_id; // Id of the calendar
	@JsonFormat(timezone = EUROPE_OSLO)
	private DateTime start;// Iso-8601 // Start time for the appointment ,
	@JsonFormat(timezone = EUROPE_OSLO)
	private DateTime end; // Iso-8601 // End time for the appointment ,,
	// These are not necessary to solve the this test, but can be included if found beneficial for the solution
	private boolean public_bookable = true;
	private boolean out_of_office;
}
