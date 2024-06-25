package com.flight.search.utils;

import java.time.Instant;
import java.time.ZoneId;

import static java.time.LocalDateTime.ofInstant;

public class TimeUtils {
    private TimeUtils() {}

    public static float getDurationInHours(Instant start, Instant end, ZoneId zoneId) {
        var departureTime = ofInstant(start, zoneId);
        var arrivalTime = ofInstant(end, zoneId);

        var arrivalTimeHoursMinis = (arrivalTime.getHour() * 60) + arrivalTime.getMinute();
        var departureTimeHoursMinis = (departureTime.getHour() * 60) + departureTime.getMinute();

        return (arrivalTimeHoursMinis - departureTimeHoursMinis) / 60f;
    }
}
