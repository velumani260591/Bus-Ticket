package com.example.Bus_Ticket_Booking.Dto;

import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Entity.Buses;
import com.example.Bus_Ticket_Booking.Entity.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.asm.Advice;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketBookingDto
{
    private Long id;
    private LocalDate BookedDate;
    private Long passengerId;
    private Long busProviderId;
    private Long busesId;
}
