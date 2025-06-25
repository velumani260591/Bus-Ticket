package com.example.Bus_Ticket_Booking.Repository;

import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusProviderRepository extends JpaRepository<BusProvider,Long>
{
    BusProvider findByEmailId(String emailId);
}
