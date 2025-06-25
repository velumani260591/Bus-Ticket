package com.example.Bus_Ticket_Booking.Service.impl;

import com.example.Bus_Ticket_Booking.Dto.PassengerDto;
import com.example.Bus_Ticket_Booking.Entity.Passenger;
import com.example.Bus_Ticket_Booking.Repository.PassengerRepository;
import com.example.Bus_Ticket_Booking.Service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Tag(
        name = "it save and get data fro the passenger"
)
@Service
@AllArgsConstructor
public class PassengerServiceImp implements PassengerService {

    private PassengerRepository passengerRepository;
    private ModelMapper modelMapper;


    @Operation(summary = "it save new passenger")
    @Override
    public PassengerDto savepassnger(PassengerDto passengerDto) {

        Passenger passenger=modelMapper.map(passengerDto,Passenger.class);

       Passenger savedPassenger= passengerRepository.save(passenger);

       return modelMapper.map(savedPassenger,PassengerDto.class);
    }

    @Operation(summary = "it get the passenger by using email")
    @Override
    public PassengerDto findByEmailid(String email) {

        Passenger passenger=passengerRepository.findByEmailId(email);
        if(passenger==null)
        {
            return null;
        }

        return modelMapper.map(passenger,PassengerDto.class);
    }
}
