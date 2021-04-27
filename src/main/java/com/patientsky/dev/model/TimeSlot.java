package com.patientsky.dev.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlot {
	private UUID id; // Id of time slot
	private UUID calendar_id; // Id of the calendar
	private UUID type_id; // Id of the calendar
	private DateTime start;// Iso-8601 // Start time for the appointment ,
	private DateTime end; // Iso-8601 // End time for the appointment ,,
	// These are not necessary to solve the this test, but can be included if found beneficial for the solution
	private boolean public_bookable = true;
	private boolean out_of_office;
}
