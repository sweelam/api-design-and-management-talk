package com.flight.service;

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
            new FlightService(cs, Mockito.mock(EmailService.class));


    @Test
    void testBookNewFlightNonExistingCustomerShouldThrowException() {
        List<Flight> allFlights = new ArrayList<>();

        Flight flightAdded =
                new Flight(UUID.randomUUID(), "Turkey-111", Instant.now() , "alex@gmail.com");

        var assertEx = assertThrows(FlightApiException.class,
                () -> flightService.bookNewFlight(flightAdded, allFlights)
        );

        assertEquals("No customer found with provided email", assertEx.getReason());

    }

    @Test
    void testBookNewFlightForDuplicateFlightsShouldThrowException() {
        List<Flight> allFlights = new ArrayList<>();

        Flight flightAdded =
                new Flight(UUID.randomUUID(), "Turkey-111", Instant.now() , "alex@gmail.com");


        when(cs.customerFound("alex@gmail.com")).thenReturn(true);

        flightService.bookNewFlight(flightAdded, allFlights);

        var assertEx = assertThrows(FlightApiException.class,
                () -> flightService.bookNewFlight(flightAdded, allFlights)
        );

        assertEquals("Flight already booked!", assertEx.getReason());

    }
}