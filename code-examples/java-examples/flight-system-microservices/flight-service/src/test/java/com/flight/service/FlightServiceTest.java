package com.flight.service;

import com.flight.config.RedisClient;
import com.flight.dto.CustomerResponse;
import com.flight.dto.Flight;
import com.flight.exceptions.FlightApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    CustomerService cs = Mockito.mock(CustomerService.class);

    FlightService flightService =
            new FlightService(cs, Mockito.mock(EmailService.class), Mockito.mock(RedisClient.class));


    @Test
    void testBookNewFlightNonExistingCustomerShouldThrowException() {
        List<Flight> allFlights = new ArrayList<>();

        Flight flightAdded =
                new Flight(UUID.randomUUID(), "Turkey-111", Instant.now() , "alex@gmail.com");

        var assertEx = assertThrows(FlightApiException.class,
                () -> flightService.bookNewFlight(flightAdded)
        );

        assertEquals("No customer found with provided email", assertEx.getReason());

    }

    @Test
    void testBookNewFlightForDuplicateFlightsShouldThrowException() {
        List<Flight> allFlights = new ArrayList<>();

        var uuid = UUID.randomUUID();
        Flight flightAdded =
                new Flight(uuid, "Turkey-111", Instant.now() , "alex@gmail.com");


        when(cs.customerFound("alex@gmail.com"))
                .thenReturn(new CustomerResponse(1, "alex", "alex@gmail.com", "Turkey-111"));

        flightService.bookNewFlight(flightAdded);

        var assertEx = assertThrows(FlightApiException.class,
                () -> flightService.bookNewFlight(flightAdded)
        );

        assertEquals("Flight already booked!", assertEx.getReason());

    }
}