package com.patientsky.dev.service;

import com.patientsky.dev.model.CalendarData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

//todo Needs so many more test cases to test all the fields values
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan
class JsonParserServiceTest {
    @Autowired
    private JsonParserService jsonParserService;

    @Test
    void shouldParseJsonToCalendarDataCorrectly() {
        final CalendarData calendarData = jsonParserService.jsonToPojo("src/test/resources/testdata1");

        //testdata1 has Danny boy.json file which contains lesser data (for testing purpose)
        assertThat(calendarData.getAppointments(), hasSize(1));
        assertThat(calendarData.getTimeslots(), hasSize(2));
        assertThat(calendarData.getTimeslottypes(), hasSize(4));
    }
}