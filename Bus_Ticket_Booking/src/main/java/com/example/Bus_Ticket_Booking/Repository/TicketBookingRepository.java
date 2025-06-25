package com.example.Bus_Ticket_Booking.Repository;

import com.example.Bus_Ticket_Booking.Entity.TicketBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TicketBookingRepository extends JpaRepository<TicketBooking,Long> {

    List<TicketBooking> findByPassengerId(Long passengerId);
    List<TicketBooking> findByBusesIdAndBookedDate(Long busId, LocalDate bookedDate);

}
