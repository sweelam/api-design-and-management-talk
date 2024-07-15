package com.flight.booking.entity;

import com.flight.booking.dto.enums.BookingStatus;
import com.flight.booking.dto.enums.BookingStatusConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "bookings", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookings_id_gen")
    @SequenceGenerator(name = "bookings_id_gen", sequenceName = "bookings_booking_id_seq", allocationSize = 1)
    @Column(name = "booking_id", nullable = false)
    private Integer id;

    @Column(name = "booking_time")
    @Builder.Default
    private Instant bookingTime = Instant.now();

    @Column(name = "status", nullable = false, length = 20)
    @Convert(converter = BookingStatusConverter.class)
    private BookingStatus status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", updatable = true)
    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Column(name = "flight_id", nullable = false)
    private Integer flightId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

}