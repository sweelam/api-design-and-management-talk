package com.flight.booking.service;

import com.flight.booking.config.RedisClient;
import com.flight.booking.exceptions.FlightApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static java.time.Instant.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    private final CustomerService cs = Mockito.mock(CustomerService.class);

    private final RedisClient redisClient = Mockito.mock(RedisClient.class);

    private final FlightService flightService =
            new FlightService(cs, Mockito.mock(EmailService.class), redisClient);


    @Test
    void testBookNewFlightNonExistingCustomerShouldThrowException() {
        Flight flightAdded =
                new Flight(UUID.randomUUID(), "Turkey-111", now() , "alex@gmail.com");

        var assertEx = assertThrows(FlightApiException.class,
                () -> flightService.bookNewFlight(flightAdded)
        );

        assertEquals("No customer found with provided email", assertEx.getReason());

    }

    @Test
    void testBookNewFlightForDuplicateFlightsShouldThrowException() {
        var uuid = UUID.randomUUID();
        Flight flightAdded =
                new Flight(uuid, "Turkey-111", now() , "alex@gmail.com");


        when(cs.customerFound("alex@gmail.com"))
                .thenReturn(new CustomerResponse(1, "alex", "alex@gmail.com", "Turkey-111"));

        when(redisClient.get("Turkey-111"))
                .thenReturn(new Flight(uuid, "Turkey-111", now(), "alex@gmail.com"));


        var assertEx = assertThrows(FlightApiException.class, () -> flightService.bookNewFlight(flightAdded));

        assertEquals("Flight already booked!", assertEx.getReason());

    }
}