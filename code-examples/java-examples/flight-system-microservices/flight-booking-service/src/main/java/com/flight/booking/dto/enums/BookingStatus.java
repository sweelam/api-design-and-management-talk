package com.flight.booking.dto.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BookingStatus {
    PENDING ("Pending"),
    CONFIRMED ("Confirmed"),
    REJECTED ("Rejected");

    private String status;

    BookingStatus(String status) {
        this.status = status;
    }

    public static BookingStatus fromStatus(String value) {
        return Arrays.stream(BookingStatus.values())
                .filter(status -> status.getStatus().equalsIgnoreCase(value))
                .findFirst().orElse(null);
    }
}
