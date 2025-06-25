package com.example.Bus_Ticket_Booking.Service.impl;

import com.example.Bus_Ticket_Booking.Dto.TicketBookingDto;
import com.example.Bus_Ticket_Booking.Entity.TicketBooking;
import com.example.Bus_Ticket_Booking.Repository.BusProviderRepository;
import com.example.Bus_Ticket_Booking.Repository.BusesRepository;
import com.example.Bus_Ticket_Booking.Repository.PassengerRepository;
import com.example.Bus_Ticket_Booking.Repository.TicketBookingRepository;
import com.example.Bus_Ticket_Booking.Service.TicketBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Tag(
        name = "this layer work was to make the tickets"
)
@Service
@AllArgsConstructor
public class TicketBookingServiceImpl implements TicketBookingService {

    private final TicketBookingRepository ticketBookingRepository;
    private final PassengerRepository passengerRepository;
    private final BusProviderRepository busProviderRepository;
    private final BusesRepository busesRepository;
    private final ModelMapper modelMapper;

    @Operation
            (summary = "it save the ticket if any user book it")
    @Override
    public TicketBookingDto saveBooking(TicketBookingDto ticketBookingDto) {

        TicketBooking booking=new TicketBooking();

        booking.setBookedDate(ticketBookingDto.getBookedDate());

        //set

        booking.setPassenger(passengerRepository.findById(ticketBookingDto.getPassengerId())
                                                                           .orElseThrow());

        booking.setBusProvider(busProviderRepository.findById(ticketBookingDto.getBusProviderId()).orElseThrow());

        booking.setBuses(busesRepository.findById(ticketBookingDto.getBusesId()).orElseThrow());

        ticketBookingRepository.save(booking);
        return modelMapper.map(booking,TicketBookingDto.class);
    }

    @Operation(summary = "it get all tickets from using the passenger id")
    @Override
    public List<TicketBooking> getTicketsByPassengerId(Long passengerId) {

        List<TicketBooking> ticketBookings=ticketBookingRepository.findByPassengerId(passengerId);

//        return ticketBookings.stream().map(a->modelMapper.map(a,TicketBookingDto.class)).toList();
        return ticketBookings;
    }

    @Operation(summary = "deleted the ticket by it id")
    @Override
    public void deleteTicket(Long id) {
        ticketBookingRepository.deleteById(id);
    }

    @Operation(summary = "it get ticket for bus operator by seraching based on day")
    @Override
    public List<TicketBooking> findByBusIdAndBookedDate(Long busId, LocalDate bookedDate) {
        return ticketBookingRepository.findByBusesIdAndBookedDate(busId, bookedDate);
    }


}
