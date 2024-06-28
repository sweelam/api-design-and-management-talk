package com.gateway.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FallBackController {

    @GetMapping("/customer-fallback")
    public Flux fallbackCustomer() {
        return Flux.empty();
    }
}
