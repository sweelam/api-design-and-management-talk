package com.flight.booking.service.impl;

public class FlightBookingServiceImpl {
//    public void bookNewFlight(final Flight flightAdded) {
//        var email = flightAdded.customerEmail();
//
//        var customerFound =
//                ofNullable(redisClient.get(email))
//                        .map(t -> (CustomerResponse) t)
//                        .orElseGet(() -> {
//                            var customerResponse = customerService.customerFound(email);
//                            redisClient.setAndGet(email, customerResponse, 2);
//                            return customerResponse;
//                        });
//
//        if (Objects.isNull(customerFound)) {
//            throw new FlightApiException("No customer found with provided email", HttpStatus.NOT_FOUND);
//        }
//
//
//        var fl = (Flight) redisClient.get(flightAdded.flightName());
//        if (fl != null && (fl.flightName().equalsIgnoreCase(flightAdded.flightName()) || fl.id().equals(flightAdded.id()))) {
//            logger.warn("Flight was already added!");
//            throw new FlightApiException("Flight already booked!", HttpStatus.BAD_REQUEST);
//        }
//
//
//        redisClient.set(flightAdded.flightName(), flightAdded, 2);
//
//        // push email event
//        emailService.sendEmail(flightAdded.customerEmail(), flightAdded.flightName());
//    }
}
