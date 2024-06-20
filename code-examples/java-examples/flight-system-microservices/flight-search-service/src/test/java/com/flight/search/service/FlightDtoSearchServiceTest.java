package com.flight.search.service;

import com.flight.search.config.RedisClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FlightDtoSearchServiceTest {

    private final CustomerService cs = Mockito.mock(CustomerService.class);

    private final RedisClient redisClient = Mockito.mock(RedisClient.class);

//    private final FlightSearchService flightSearchService =
//            new FlightSearchService(cs, Mockito.mock(EmailService.class), redisClient);

}