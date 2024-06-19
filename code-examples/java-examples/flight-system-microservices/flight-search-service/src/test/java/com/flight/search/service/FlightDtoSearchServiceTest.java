package com.flight.search.service;

import com.flight.search.config.RedisClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FlightDtoSearchServiceTest {

    private final CustomerService cs = Mockito.mock(CustomerService.class);

    private final RedisClient redisClient = Mockito.mock(RedisClient.class);

//    private final FlightSearchService flightSearchService =
//            new FlightSearchService(cs, Mockito.mock(EmailService.class), redisClient);


    @Test
    void testBookNewFlightNonExistingCustomerShouldThrowException() {
//        FlightDto flightDtoAdded =
//                new FlightDto(UUID.randomUUID(), "Turkey-111", now() , "alex@gmail.com");
//
//        var assertEx = assertThrows(FlightApiException.class,
//                () -> flightSearchService.bookNewFlight(flightDtoAdded)
//        );
//
//        assertEquals("No customer found with provided email", assertEx.getReason());

    }

    @Test
    void testBookNewFlightForDuplicateFlightsShouldThrowException() {
//        var uuid = UUID.randomUUID();
//        FlightDto flightDtoAdded =
//                new FlightDto(uuid, "Turkey-111", now() , "alex@gmail.com");
//
//
//        when(cs.customerFound("alex@gmail.com"))
//                .thenReturn(new CustomerResponse(1, "alex", "alex@gmail.com", "Turkey-111"));
//
//        when(redisClient.get("Turkey-111"))
//                .thenReturn(new FlightDto(uuid, "Turkey-111", now(), "alex@gmail.com"));
//
//
//        var assertEx = assertThrows(FlightApiException.class, () -> flightSearchService.bookNewFlight(flightDtoAdded));
//
//        assertEquals("Flight already booked!", assertEx.getReason());

    }
}