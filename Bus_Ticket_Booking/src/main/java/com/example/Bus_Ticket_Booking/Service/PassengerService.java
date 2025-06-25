package com.example.Bus_Ticket_Booking.Service;

import com.example.Bus_Ticket_Booking.Dto.PassengerDto;

public interface PassengerService {

    PassengerDto savepassnger(PassengerDto passengerDto);

    PassengerDto findByEmailid(String email);
}
