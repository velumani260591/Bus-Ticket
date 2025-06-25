package com.example.Bus_Ticket_Booking.Service;

import com.example.Bus_Ticket_Booking.Dto.TicketBookingDto;
import com.example.Bus_Ticket_Booking.Entity.TicketBooking;

import java.time.LocalDate;
import java.util.List;

public interface TicketBookingService
{
    TicketBookingDto saveBooking(TicketBookingDto ticketBookingDto);
    List<TicketBooking> getTicketsByPassengerId(Long passengerId);
    void deleteTicket(Long id);
    List<TicketBooking> findByBusIdAndBookedDate(Long busId, LocalDate bookedDate);
}
