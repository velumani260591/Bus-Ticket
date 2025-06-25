package com.example.Bus_Ticket_Booking.Entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Tag(
        name = "it store the customer data"
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String emailId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long phoneNumber;


    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    List<TicketBooking> ticketBookings;

}
