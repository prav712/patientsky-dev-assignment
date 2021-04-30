package com.patientsky.dev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
	private UUID id;  // Id of appointment
	private UUID patient_id; // Id of the patient having the appointment
	private UUID calendar_id; // Id of the calendar
	private DateTime start;// Iso-8601 // Start time for the appointment ,
	private DateTime end; // Iso-8601 // End time for the appointment ,
	// These are not necessary to solve the this test, but can be included if found beneficial for the solution
	private String patient_comment; // Comment from patient,
	private String note;// note from calendar,
	private UUID time_slot_type_id; // Id of the timeslot type used setup appointment,
	private UUID type_id; // Id of the type of appointment,
	private int state;// Coded value of the status of the appointment,
	private String out_of_office_location;
	private boolean out_of_office;//		"out_of_office":false,
	private boolean completed; // has the doctor marked the appointment as completed,
	private boolean scheduled; // Was the appointment created as part ofweek plan
}
