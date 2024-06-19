package com.flight.search.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FlightSearchControllerTest {
    private static final String FLIGHT_SERACH_URL = "/";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllFlights() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get(FLIGHT_SERACH_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = mvcResult.getResponse().getContentAsString();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
    }

    @Test
    void getFlightByFlightNumber() {
    }
}