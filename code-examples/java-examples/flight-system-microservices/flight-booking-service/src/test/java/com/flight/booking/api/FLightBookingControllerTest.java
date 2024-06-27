package com.flight.booking.api;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class FLightBookingControllerTest extends IntegrationTestSupport {
    private final String FLIGHT_BOOKING_URL = "/bookings";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void bookFlight_ShouldReturnOk() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(FLIGHT_BOOKING_URL).accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertNotNull(contentAsString);
        assertFalse(contentAsString.isEmpty());
    }
}
