package com.example.Bus_Ticket_Booking.Repository;

import com.example.Bus_Ticket_Booking.Entity.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PassengerRepositoryTest
{
    @TestConfiguration
    static class TestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Passenger passenger;

    @BeforeEach
    public void setUp()
    {
         passenger=new Passenger();

        passenger.setName("Thrivendra");
        passenger.setEmailId("test@gmail.com");
        passenger.setPassword(passwordEncoder.encode("passs"));
        passenger.setPhoneNumber(1234567890L);
    }

    @Test
    public void givenPassenger_whenSave_thenReturnPassenger()
    {
        //given (we take the setup data

        //when

        Passenger savedpassenger=passengerRepository.save(passenger);
        //then
        assertThat(savedpassenger).isNotNull();
    }

    @Test
    public void givenPassenger_whenFindPassengerByEmailId_thenReturnPassenger()
    {
        //given

        String email="test@gmail.com";
        passengerRepository.save(passenger);

        //when

        Passenger oldpassenger=passengerRepository.findByEmailId(email);

        //then
        assertThat(oldpassenger).isNotNull();
        assertThat(oldpassenger.getEmailId()).isEqualTo(email);
    }
}
