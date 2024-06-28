package com.flight.search.service;

import com.flight.search.dto.CustomerResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
    private final RestTemplate restTemplate;

    public CustomerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CustomerResponse customerFound(String customerEmail) {
        return restTemplate.getForObject("http://localhost:8888/api/v1/customers/" + customerEmail, CustomerResponse.class);
    }

}
