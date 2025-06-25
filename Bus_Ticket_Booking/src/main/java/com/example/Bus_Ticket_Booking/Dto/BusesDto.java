package com.example.Bus_Ticket_Booking.Dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusesDto
{
    private Long id;

    private String busName;

    private String busNumber;
    private String startingPoint;
    private String startTime;
    private String destinationPoint;
    private String destinationTime;
    private double price;
    private  Long busProvider_id;
}
