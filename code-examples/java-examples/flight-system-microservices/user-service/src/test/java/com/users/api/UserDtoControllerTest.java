package com.users.api;

import com.users.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.util.UUID.randomUUID;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(RestDocumentationExtension.class)
class UserDtoControllerTest {
    private final String URL = "/api/v1/customers";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void testGetAllCustomersShouldReturnOk() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(URL)
        ).andExpect(status().isOk());
    }

    @Test
    void testGetCustomerByEmailShouldReturnOk() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(URL + "/md.ahmed@gmail.com")
        ).andExpect(status().isOk())
                .andDo(document("getCustomerByEmail"));
    }

    @Test
    void registerNewCustomerShouldReturnCreatedCode() throws Exception {
        var newCustomer = new UserDto(0, "bahaa ahmed", "bahaa@gmail.com", randomUUID().toString());

        mockMvc.perform(
                MockMvcRequestBuilders.post(URL + "/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(newCustomer))
        ).andExpect(status().isCreated())
                .andDo(
                        document("registerCustomer", responseFields(
                                    fieldWithPath("id").description("customer database id"),
                                    fieldWithPath("name").description("customer name"),
                                    fieldWithPath("email").description("customer email"),
                                    fieldWithPath("flightNumber").description("customer flight code or number")
                                ))
                );
    }
}