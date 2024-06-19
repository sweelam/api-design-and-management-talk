package com.flight.search.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "flights", schema = "public")
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flights_id_gen")
    @SequenceGenerator(name = "flights_id_gen", sequenceName = "flights_flight_id_seq", allocationSize = 1)
    @Column(name = "flight_id", nullable = false)
    private Integer id;

    @Column(name = "flight_number", nullable = false, length = 10)
    private String flightNumber;


    @Column(name = "departure_airport", nullable = false, length = 100)
    private String departureAirport;


    @Column(name = "arrival_airport", nullable = false, length = 100)
    private String arrivalAirport;

    
    @Column(name = "departure_time", nullable = false)
    private Instant departureTime;

    
    @Column(name = "arrival_time", nullable = false)
    private Instant arrivalTime;

    
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;


    @Builder.Default
    @Column(name = "created_at", insertable = true, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", insertable = true, updatable = true)
    private Instant updatedAt;

}