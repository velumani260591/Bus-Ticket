package com.example.Bus_Ticket_Booking.Entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Tag(
        name = "it store the all tickets"
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate bookedDate;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "bus_provider_id")
    private BusProvider busProvider;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Buses buses;
}
