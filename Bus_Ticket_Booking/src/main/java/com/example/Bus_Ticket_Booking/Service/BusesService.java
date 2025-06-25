package com.example.Bus_Ticket_Booking.Service;

import com.example.Bus_Ticket_Booking.Dto.BusesDto;
import com.example.Bus_Ticket_Booking.Entity.Buses;

import java.util.List;

public interface BusesService
{
    BusesDto saveBueses(BusesDto busesDto);
    List<BusesDto> findBusesByBusProviderEmailId(String emailId);
    void deleteBusById(Long id);

    BusesDto findyById(Long id);

    BusesDto updateTheBus(BusesDto busesDto);

    List<BusesDto> findByRoute(String form,String to);

    Buses findById(Long id);
}
