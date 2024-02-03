package com.customers.api;

import com.customers.dto.Customer;
import com.customers.exceptions.CustomerApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final List<Customer> allCustomers = new ArrayList(
            List.of(
            new Customer(1, "Mohamed Ahmed", "md.ahmed@gmail.com", randomUUID().toString()),
            new Customer(1, "Alex Mon", "alex.mon@gmail.com", randomUUID().toString()),
            new Customer(1, "Mia Andrey", "mia.andrey@gmail.com", randomUUID().toString()),
            new Customer(1, "Hesham Masoud", "hesham.masoud@gmail.com", randomUUID().toString())
        )
    );

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{email}")
    public DeferredResult<ResponseEntity<Customer>> getCustomerByEmail(@PathVariable String email) {
        var customerOtp =  allCustomers.stream()
                .filter(customer -> email.equals(customer.email()))
                .findAny();

        if (!customerOtp.isPresent()) {
            throw new CustomerApiException("No customer found with provided email", HttpStatus.NOT_FOUND);
        }

        var res = new DeferredResult<ResponseEntity<Customer>>();
        res.setResult(ResponseEntity.ok(customerOtp.get()));

        return res;
    }

    @PostMapping("/registration")
    public ResponseEntity<Customer> registerNewCustomer(@RequestBody Customer customerRequest) {
        var newCustomer = new Customer(allCustomers.size() + 1, customerRequest.name(),
                customerRequest.email(), randomUUID().toString());

        allCustomers.stream()
                .filter(customer -> customerRequest.email().equals(customer.email()))
                .findAny()
                .ifPresent(t -> {
                            throw new CustomerApiException("Customer already registered", HttpStatus.BAD_REQUEST);
                });

        allCustomers.add(newCustomer);

        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }
}
