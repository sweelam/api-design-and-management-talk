package com.flight.search.api;

import com.flight.search.infra.IntegrationTestSupport;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class FlightSearchControllerTest extends IntegrationTestSupport {
    private static final String FLIGHT_SEARCH_URL = "/";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllFlights_ShouldReturnOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get(FLIGHT_SEARCH_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = mvcResult.getResponse().getContentAsString();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
    }


    @Test
    void getFlightByFlightNumber_ShouldReturnOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get(FLIGHT_SEARCH_URL).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("flightNumber", "FL102")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseBody = mvcResult.getResponse().getContentAsString();
        DocumentContext parsedResponse = JsonPath.parse(responseBody);
        assertEquals(3, parsedResponse.read("$.[0].id", Integer.class));
    }

    @Test
    void getFlightByWrongFlightNumber_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(
                get(FLIGHT_SEARCH_URL).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("flightNumber", "123")
        ).andExpect(status().isBadRequest());
    }


    @Test
    void getFlightFromTo_ShouldReturnOk() throws Exception {
        MvcResult result = mockMvc.perform(
                get(FLIGHT_SEARCH_URL + "direct-path").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "LAX")
                        .param("to", "JFK")
        ).andReturn();

        MvcResult mvcResult = mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = mvcResult.getResponse().getContentAsString();
        DocumentContext parsedResponse = JsonPath.parse(responseBody);

        assertEquals(1, parsedResponse.read("$.size()", Integer.class));
        assertEquals(7, parsedResponse.read("$.[0].size()", Integer.class));
    }

    @Test
    void getShortestFlightFromTo_ShouldReturnOk() throws Exception {
        MvcResult result = mockMvc.perform(
                get(FLIGHT_SEARCH_URL + "shortest-path").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "LAX")
                        .param("to", "JFK")
        ).andReturn();

        MvcResult mvcResult = mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andReturn();

//        var responseBody = mvcResult.getResponse().getContentAsString();
//        DocumentContext parsedResponse = JsonPath.parse(responseBody);
//
//        assertEquals(1, parsedResponse.read("$.size()", Integer.class));
//        assertEquals(7, parsedResponse.read("$.[0].size()", Integer.class));
    }

}