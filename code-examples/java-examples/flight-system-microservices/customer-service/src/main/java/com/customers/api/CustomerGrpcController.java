package com.customers.api;


import com.customer.grpc.CustomerRequestMessage;
import com.customer.grpc.CustomerResponseMessage;
import com.customer.grpc.CustomerServiceGrpc;
import com.customers.dto.Customer;
import com.customers.exceptions.CustomerApiException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.of;
import static java.util.UUID.randomUUID;

@GrpcService
public class CustomerGrpcController extends CustomerServiceGrpc.CustomerServiceImplBase {
    private final Map<String, Customer> emailToCustomerMap;

    public CustomerGrpcController() {
        List<Customer> allCustomers = List.of(
                new Customer(1, "Mohamed Ahmed", "md.ahmed@gmail.com", UUID.randomUUID().toString()),
                new Customer(2, "Alex Mon", "alex.mon@gmail.com", UUID.randomUUID().toString()),
                new Customer(3, "Mia Andrey", "mia.andrey@gmail.com", UUID.randomUUID().toString()),
                new Customer(4, "Hesham Masoud", "hesham.masoud@gmail.com", UUID.randomUUID().toString())
        );

        // Convert list to a map for faster lookup
        this.emailToCustomerMap = allCustomers.stream()
                .collect(Collectors.toMap(Customer::email, Function.identity()));
    }

    @Override
    public void getCustomerByEmail(CustomerRequestMessage request,
                                   StreamObserver<CustomerResponseMessage> responseObserver) {

        Optional.ofNullable(emailToCustomerMap.get(request.getCustomerEmail()))
                .map(customer -> CustomerResponseMessage.newBuilder()
                        .setEmail(customer.email())
                        .setFlightNumber(customer.flightNumber())
                        .setName(customer.name())
                        .setId(customer.id())
                        .build())
                .ifPresentOrElse(responseObserver::onNext, () -> {
                    throw new CustomerApiException("No customer found with provided email", HttpStatus.NOT_FOUND);
                });

        responseObserver.onCompleted();
    }
}
