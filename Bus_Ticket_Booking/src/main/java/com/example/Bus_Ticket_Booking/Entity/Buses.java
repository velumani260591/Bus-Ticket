package com.example.Bus_Ticket_Booking.Entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Tag(
        name = "table for string bus details"
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Buses
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String busName;

    @Column(nullable = false)
    private String busNumber;

    @Column(nullable = false)
    private String startingPoint;

    private String startTime;

    @Column(nullable = false)
    private String destinationPoint;

    private String destinationTime;


    @Column(nullable = false)
    private double price;


    @ManyToOne
    @JoinColumn(name = "bus_provider_id")
    private BusProvider busProvider;



}
