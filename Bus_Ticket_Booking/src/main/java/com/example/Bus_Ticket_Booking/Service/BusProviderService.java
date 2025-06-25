package com.example.Bus_Ticket_Booking.Service;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Entity.BusProvider;

public interface BusProviderService {
    BusProviderDto saveBusProvider(BusProviderDto busProviderDto);

    BusProviderDto findBusProviderByEmailId(String emailId);

    BusProviderDto findById(Long id);
}
