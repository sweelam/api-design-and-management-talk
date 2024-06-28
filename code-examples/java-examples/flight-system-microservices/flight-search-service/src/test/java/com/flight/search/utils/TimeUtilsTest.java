package com.flight.search.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;

import static com.flight.search.utils.TimeUtils.getDurationInHours;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeUtilsTest {

    @Test
    void getDurationInHoursReturnTimeCorrectly() {
        var now = Instant.parse("2024-01-01T00:00:00Z");
        var after = Instant.parse("2024-01-01T01:30:00Z");
        assertEquals(1.5, getDurationInHours(now, after, ZoneOffset.UTC));
    }
}