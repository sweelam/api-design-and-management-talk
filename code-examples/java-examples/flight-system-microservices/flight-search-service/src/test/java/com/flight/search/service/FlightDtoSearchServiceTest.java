package com.flight.search.service;

import com.flight.search.infra.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FlightDtoSearchServiceTest extends IntegrationTestSupport {
    @Autowired
    private FlightSearchService flightSearchService;


    @Test
    void findPath_ShouldReturnCorrectPath() {
        List<String> path = flightSearchService.shortestPath("JFK", "ATL");
        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals("[JFK, LAX, ATL]", path.toString());
    }

}