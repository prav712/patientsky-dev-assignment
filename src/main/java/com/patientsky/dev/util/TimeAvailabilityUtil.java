package com.patientsky.dev.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeAvailabilityUtil {
    public static final String EUROPE_OSLO = "Europe/Oslo";
    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    private TimeAvailabilityUtil() {
        throw new IllegalStateException("Utility class");
    }
}
