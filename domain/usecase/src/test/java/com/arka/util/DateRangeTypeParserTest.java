package com.arka.util;

import com.arka.report.dto.InstantDateRange;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTypeParserTest {

    @Test
    void shouldConvertDateRangeToInstantInLocalTimezone() {

        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 1, 31);

        InstantDateRange result = DateRangeTypeParser.toInstant(start, end);

        ZoneId zone = ZoneId.of("America/Bogota");
        assertEquals(start.atStartOfDay(zone).toInstant(), result.start());
        assertEquals(end.plusDays(1).atStartOfDay(zone).toInstant(), result.end());
    }

    @Test
    void shouldSetEndToStartOfNextDay() {

        LocalDate start = LocalDate.of(2026, 6, 15);
        LocalDate end = LocalDate.of(2026, 6, 15);

        InstantDateRange result = DateRangeTypeParser.toInstant(start, end);

        assertNotEquals(result.start(), result.end());
    }
}