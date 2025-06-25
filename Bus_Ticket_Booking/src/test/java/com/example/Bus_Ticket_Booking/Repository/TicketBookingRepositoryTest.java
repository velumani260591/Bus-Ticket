package com.example.Bus_Ticket_Booking.Repository;

import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Entity.Buses;
import com.example.Bus_Ticket_Booking.Entity.Passenger;
import com.example.Bus_Ticket_Booking.Entity.TicketBooking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TicketBookingRepositoryTest {

    @TestConfiguration
    static class config
    {
        @Bean
        public PasswordEncoder passwordEncoder()
        {
            return new BCryptPasswordEncoder();
        }
    }
    @Autowired
    private TicketBookingRepository ticketBookingRepository;
    @Autowired PassengerRepository passengerRepository;
    @Autowired BusesRepository busesRepository;
    @Autowired BusProviderRepository busProviderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Buses buses;
    private BusProvider busProvider;
    private Passenger passenger;
    private TicketBooking ticketBooking;


    @BeforeEach
    public void setUp()
    {
        busProvider=new BusProvider();
        busProvider.setName("tests");
        busProvider.setEmailId("test@gmail.com");
        busProvider.setPassword(passwordEncoder.encode("test"));
        busProvider.setPhoneNumber(1234567890L);

        buses=new Buses();
        buses.setBusName("test");
        buses.setBusNumber("xyz-1234");
        buses.setStartingPoint("salem");
        buses.setStartTime("11:00 Am");
        buses.setDestinationPoint("chennai");
        buses.setDestinationTime("6:00 Pm");
        buses.setPrice(800);
        buses.setBusProvider(busProvider);

        passenger=new Passenger();

        passenger.setName("Thrivendra");
        passenger.setEmailId("test@gmail.com");
        passenger.setPassword(passwordEncoder.encode("passs"));
        passenger.setPhoneNumber(1234567890L);


        ticketBooking=new TicketBooking();

        ticketBooking.setBookedDate(LocalDate.now());
        ticketBooking.setPassenger(passenger);
        ticketBooking.setBusProvider(busProvider);
        ticketBooking.setBuses(buses);

    }

    @Test
    public void givenTicketBooking_whenSaveTheTicket_thenReturnTicket()
    {
        //given taken the data from the setup()

        //when
        TicketBooking savedticket=ticketBookingRepository.save(ticketBooking);

        //then
        assertThat(savedticket).isNotNull();
        assertThat(savedticket.getPassenger().getEmailId()).isEqualTo("test@gmail.com");
        assertThat(savedticket.getBusProvider().getEmailId()).isEqualTo("test@gmail.com");
        assertThat(savedticket.getBuses().getBusName()).isEqualTo("test");
    }

    @Test
    public void givenTicketBookings_whenFindByPassengerId_theReturnTicket()
    {
        //given
        Buses buses1=busesRepository.save(buses);
        Passenger passenger1=passengerRepository.save(passenger);
        BusProvider busProvider1=busProviderRepository.save(busProvider);
        TicketBooking savedticket=ticketBookingRepository.save(ticketBooking);

        //when
        List<TicketBooking> ticketBookingList=ticketBookingRepository.findByPassengerId(passenger1.getId());

        //then
        assertThat(ticketBookingList).isNotEmpty();

    }

    @Test
    public void givenTickets_whenDeleteById_theNoReturn()
    {
        TicketBooking savedticket=ticketBookingRepository.save(ticketBooking);

        ticketBookingRepository.deleteById(savedticket.getId());
    }

}
