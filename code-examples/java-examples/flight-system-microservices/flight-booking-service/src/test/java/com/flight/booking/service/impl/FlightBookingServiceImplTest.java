package com.flight.booking.service.impl;

import com.flight.booking.exceptions.BookingApiException;
import com.flight.booking.infra.IntegrationTestSupport;
import com.flight.booking.service.FlightBookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FlightBookingServiceImplTest extends IntegrationTestSupport {
    @Autowired
    private FlightBookingService flightBookingService;

    @Test
    void getBookings() {
        assertThat(flightBookingService.getBookings())
                .isNotNull()
                .isNotEmpty()
                .hasSize(10);
    }

    @Test
    void getBookingById_ShouldReturnBookingDto() {
        assertThat(flightBookingService.getBookingById(1)).isNotNull();
    }

    @Test
    void getBookingByWrongId_ShouldThrowException() {
        assertThatThrownBy(() -> flightBookingService.getBookingById(-1))
                .isInstanceOf(BookingApiException.class)
                .hasMessageContaining("Booking not found");
    }

    @Test
    void createBooking() {
    }

    @Test
    void updateBooking() {
    }
}