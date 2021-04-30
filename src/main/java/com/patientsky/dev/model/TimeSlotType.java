package com.patientsky.dev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotType {
	private UUID id; // Id of the time slot type,
	private String name;
	private int slot_size; // duration in minutes,
	// These are not necessary to solve the bonus assignment, but can be included if found beneficial for the solution
	private boolean public_bookable;
	private String color = "#ccc";
	private String icon = "icon-home";
	private String clinic_id = "00000000-0000-4000-a002-000000000002";
	private String deleted = null;
	private boolean out_of_office;
	private boolean enabled;
}
