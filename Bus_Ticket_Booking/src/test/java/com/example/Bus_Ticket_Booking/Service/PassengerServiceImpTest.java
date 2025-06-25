package com.example.Bus_Ticket_Booking.Service;

import com.example.Bus_Ticket_Booking.Dto.PassengerDto;
import com.example.Bus_Ticket_Booking.Entity.Passenger;
import com.example.Bus_Ticket_Booking.Repository.PassengerRepository;
import com.example.Bus_Ticket_Booking.Service.impl.PassengerServiceImp;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImpTest
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
    private PassengerRepository passengerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PassengerServiceImp passengerService;

    private Passenger passenger;
    private PassengerDto passengerDto;

    @BeforeEach
    public void setUp()
    {
        passenger=new Passenger();
        passenger.setName("Thrivendra");
        passenger.setEmailId("test@gmail.com");
        passenger.setPassword(passwordEncoder.encode("passs"));
        passenger.setPhoneNumber(1234567890L);

        passengerDto =new PassengerDto();
        passengerDto.setName("Thrivendra");
        passengerDto.setEmailId("test@gmail.com");
        passengerDto.setPassword(passwordEncoder.encode("passs"));
        passengerDto.setPhoneNumber(1234567890L);

    }

    @Test
    public void givenPassengerDetails_whenSavePassenger_theReturnPassenger()
    {
        when(modelMapper.map(passengerDto,Passenger.class)).thenReturn(passenger);
        when(passengerRepository.save(passenger)).thenReturn(passenger);

        when(modelMapper.map(passenger,PassengerDto.class)).thenReturn(passengerDto);

        PassengerDto passengerDto1=passengerService.savepassnger(passengerDto);

        assertThat(passengerDto1).isNotNull();
        assertThat(passengerDto1.getEmailId()).isEqualTo("test@gmail.com");
    }

    @Test
    public void givenPassengerDetails_whenFindByEmailId_theReturnPassenger() {


        String email="test@gmail.com";

        //mock
        when(passengerRepository.findByEmailId(email)).thenReturn(passenger);
        when(modelMapper.map(passenger,PassengerDto.class)).thenReturn(passengerDto);

        PassengerDto passengerDto1=passengerService.findByEmailid(email);

        assertThat(passengerDto1.getEmailId()).isEqualTo(email);
    }
}
