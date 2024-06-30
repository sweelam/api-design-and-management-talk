package com.flight.booking.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.booking.dto.BookingDto;
import com.flight.booking.infra.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class FLightBookingControllerTest extends IntegrationTestSupport {
    private final String FLIGHT_BOOKING_URL = "/bookings";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBookings_ShouldReturnOk() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(FLIGHT_BOOKING_URL).accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        assertNotNull(contentAsString);
        assertFalse(contentAsString.isEmpty());
    }

    @Test
    void getBookingsById_ShouldReturnOk() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(FLIGHT_BOOKING_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("bookingId", "1")
                ).andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        assertNotNull(contentAsString);
        assertFalse(contentAsString.isEmpty());
    }

    @Test
    void bookFlight_ShouldReturnOk() throws Exception {
        var request = new BookingDto(null, 2, 1, null, null);
        MvcResult mvcResult = mockMvc.perform(
                post(FLIGHT_BOOKING_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andReturn();


        mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isCreated());
    }

    @Test
    void bookFlight_ShouldReturnBadRequest() throws Exception {
        var request = new BookingDto(null, null, 1, null, null);
        mockMvc.perform(
                post(FLIGHT_BOOKING_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void bookFlightWithWrongBookingId_ShouldReturnBadRequest() throws Exception {
        var request = new BookingDto(null, -2, 1, null, null);

        MvcResult mvcResult = mockMvc.perform(
                post(FLIGHT_BOOKING_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andReturn();

        mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isBadRequest());
    }
}
