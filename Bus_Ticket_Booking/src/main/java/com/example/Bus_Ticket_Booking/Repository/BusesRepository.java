package com.example.Bus_Ticket_Booking.Repository;

import com.example.Bus_Ticket_Booking.Entity.Buses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusesRepository extends JpaRepository<Buses,Long>
{
    List<Buses> findByBusProviderId(Long id);

    List<Buses> findByStartingPointIgnoreCaseAndDestinationPointIgnoreCase(String startingPoint, String destinationPoint);

}
