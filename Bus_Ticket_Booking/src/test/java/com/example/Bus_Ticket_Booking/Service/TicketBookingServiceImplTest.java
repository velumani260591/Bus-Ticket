package com.example.Bus_Ticket_Booking.Service;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Dto.BusesDto;
import com.example.Bus_Ticket_Booking.Dto.PassengerDto;
import com.example.Bus_Ticket_Booking.Dto.TicketBookingDto;
import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Entity.Buses;
import com.example.Bus_Ticket_Booking.Entity.Passenger;
import com.example.Bus_Ticket_Booking.Entity.TicketBooking;
import com.example.Bus_Ticket_Booking.Repository.BusProviderRepository;
import com.example.Bus_Ticket_Booking.Repository.BusesRepository;
import com.example.Bus_Ticket_Booking.Repository.PassengerRepository;
import com.example.Bus_Ticket_Booking.Repository.TicketBookingRepository;
import com.example.Bus_Ticket_Booking.Service.impl.TicketBookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketBookingServiceImplTest
{
    @TestConfiguration
    static class config
    {
        @Bean
        public PasswordEncoder passwordEncoder()
        {
            return new BCryptPasswordEncoder();
        }
    }

    @Mock
    private  TicketBookingRepository ticketBookingRepository;
    @Mock
    private  PassengerRepository passengerRepository;
    @Mock
    private  BusProviderRepository busProviderRepository;
    @Mock
    private  BusesRepository busesRepository;
    @Mock
    private  ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TicketBookingServiceImpl ticketBookingServiceimpl;


    private Buses buses;
    private BusesDto busesDto;
    private BusProvider busProvider;
    private BusProviderDto busProviderDto;
    private Passenger passenger;
    private PassengerDto passengerDto;
    private TicketBooking ticketBooking;
    private TicketBookingDto ticketBookingDto;

    @BeforeEach
    public void setUp()
    {
        busProvider=new BusProvider();
        busProvider.setName("tests");
        busProvider.setEmailId("test@gmail.com");
        busProvider.setPassword(passwordEncoder.encode("test"));
        busProvider.setPhoneNumber(1234567890L);

        busProviderDto =new BusProviderDto();
        busProviderDto.setId(1L);
        busProviderDto.setName("tests");
        busProviderDto.setEmailId("test@gmail.com");
        busProviderDto.setPassword(passwordEncoder.encode("test"));
        busProviderDto.setPhoneNumber(1234567890L);

        buses=new Buses();

        buses.setBusName("test");
        buses.setBusNumber("xyz-1234");
        buses.setStartingPoint("salem");
        buses.setStartTime("11:00 Am");
        buses.setDestinationPoint("chennai");
        buses.setDestinationTime("6:00 Pm");
        buses.setPrice(800);
        buses.setBusProvider(busProvider);

        busesDto=new BusesDto();
        busesDto.setId(1L);
        busesDto.setBusName("test");
        busesDto.setBusNumber("xyz-1234");
        busesDto.setStartingPoint("salem");
        busesDto.setStartTime("11:00 Am");
        busesDto.setDestinationPoint("chennai");
        busesDto.setDestinationTime("6:00 Pm");
        busesDto.setPrice(800);


        passenger=new Passenger();
        passenger.setName("Thrivendra");
        passenger.setEmailId("test@gmail.com");
        passenger.setPassword(passwordEncoder.encode("passs"));
        passenger.setPhoneNumber(1234567890L);

        passengerDto =new PassengerDto();
        passengerDto.setId(1L);
        passengerDto.setName("Thrivendra");
        passengerDto.setEmailId("test@gmail.com");
        passengerDto.setPassword(passwordEncoder.encode("passs"));
        passengerDto.setPhoneNumber(1234567890L);


        ticketBooking=new TicketBooking();

        ticketBooking.setBookedDate(LocalDate.now());
        ticketBooking.setPassenger(passenger);
        ticketBooking.setBusProvider(busProvider);
        ticketBooking.setBuses(buses);

        ticketBookingDto=new TicketBookingDto();
        ticketBookingDto.setId(1L);
        ticketBookingDto.setBookedDate(LocalDate.now());
        ticketBookingDto.setBusesId(1L);
        ticketBookingDto.setBusProviderId(1L);
        ticketBookingDto.setPassengerId(1L);

    }

    @Test
    public void givenTicketBookingDto_whenSaveBooking_thenReturnTicketBookingDto() {

        when(passengerRepository.findById(ticketBookingDto.getPassengerId()))
                .thenReturn(Optional.of(passenger));
        when(busProviderRepository.findById(ticketBookingDto.getBusProviderId()))
                .thenReturn(Optional.of(busProvider));
        when(busesRepository.findById(ticketBookingDto.getBusesId()))
                .thenReturn(Optional.of(buses));

        when(ticketBookingRepository.save(org.mockito.ArgumentMatchers.any(TicketBooking.class)))
                .thenReturn(ticketBooking);
        when(modelMapper.map(ticketBooking, TicketBookingDto.class))
                .thenReturn(ticketBookingDto);

        TicketBookingDto result = ticketBookingServiceimpl.saveBooking(ticketBookingDto);

        assertThat(result).isNotNull();
        assertThat(result.getBookedDate()).isEqualTo(ticketBookingDto.getBookedDate());
        assertThat(result.getPassengerId()).isEqualTo(ticketBookingDto.getPassengerId());
        assertThat(result.getBusProviderId()).isEqualTo(ticketBookingDto.getBusProviderId());
        assertThat(result.getBusesId()).isEqualTo(ticketBookingDto.getBusesId());

        // Optional: Verify that save was called
//        verify(ticketBookingRepository).save(org.mockito.ArgumentMatchers.any(TicketBooking.class));
    }

    @Test
    public void givenTicketId_whenDeleteTicket_thenRepositoryDeleteByIdCalled() {
        // given
        Long ticketId = 1L;

        // when
        ticketBookingServiceimpl.deleteTicket(ticketId);

        // then
        verify(ticketBookingRepository).deleteById(ticketId);
    }

    @Test
    public void givenBusIdAndDate_whenFindByBusIdAndBookedDate_thenReturnTicketList() {
        // given
        Long busId = 1L;
        LocalDate date = LocalDate.now();
        TicketBooking ticket = new TicketBooking();
        List<TicketBooking> tickets = Collections.singletonList(ticket);

        when(ticketBookingRepository.findByBusesIdAndBookedDate(busId, date)).thenReturn(tickets);

        // when
        List<TicketBooking> result = ticketBookingServiceimpl.findByBusIdAndBookedDate(busId, date);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(ticket);
    }
}


