package com.flight.booking.builders;

import com.google.gson.Gson;

import java.time.Instant;

public final class EmailBuilders {
    private static final String APPLICATION_NAME = "flight-service";
    private static final String MESSAGE_BODY = "Your flight %s booking on date %s has been confirmed";
    private EmailBuilders() {}

    private static final Gson gson = new Gson();

    public static String buildEmailEvent(final String customerEmail, final String flightName) {
        Instant now = Instant.now();
        return gson.toJson(
                new EmailEvent(customerEmail, flightName, MESSAGE_BODY.formatted(flightName, now), APPLICATION_NAME, now.toString())
        );
    }

    private record EmailEvent(String customerEmail, String flightUuid, String messageBody, String producer, String eventTime) {}
}
