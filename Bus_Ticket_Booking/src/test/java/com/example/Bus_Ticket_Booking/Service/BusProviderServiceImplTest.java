package com.example.Bus_Ticket_Booking.Service;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Repository.BusProviderRepository;
import com.example.Bus_Ticket_Booking.Service.impl.BusProviderServiceImpl;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusProviderServiceImplTest
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
    private BusProviderRepository busProviderRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private BusProviderServiceImpl busProviderService;

    private BusProvider busProvider;
    private BusProviderDto busProviderDto;

    @BeforeEach
    public void setUp()
    {
        busProvider=new BusProvider();
        busProvider.setName("tests");
        busProvider.setEmailId("test@gmail.com");
        busProvider.setPassword(passwordEncoder.encode("test"));
        busProvider.setPhoneNumber(1234567890L);

        busProviderDto=new BusProviderDto();
        busProviderDto.setName("tests");
        busProviderDto.setEmailId("test@gmail.com");
        busProviderDto.setPassword(passwordEncoder.encode("test"));
        busProviderDto.setPhoneNumber(1234567890L);
    }

    @Test
    public void givenBusProvider_whenSaveBusProvider_theReturnBusProvider()
    {
        when(modelMapper.map(busProviderDto,BusProvider.class)).thenReturn(busProvider);
        when(busProviderRepository.save(busProvider)).thenReturn(busProvider);
        when(modelMapper.map(busProvider,BusProviderDto.class)).thenReturn(busProviderDto);

        BusProviderDto busProviderDto1=busProviderService.saveBusProvider(busProviderDto);

        assertThat(busProviderDto1).isNotNull();
        assertThat(busProviderDto1.getEmailId()).isEqualTo("test@gmail.com");
    }

    @Test
    public void givenBusProvider_whenFindBusProviderByEmailId_theReturnBusProvider()
    {
        String email="test@gmail.com";

        when(busProviderRepository.findByEmailId(email)).thenReturn(busProvider);
        when(modelMapper.map(busProvider,BusProviderDto.class)).thenReturn(busProviderDto);


        BusProviderDto busProviderDto1=busProviderService.findBusProviderByEmailId(email);

        assertThat(busProviderDto1.getEmailId()).isEqualTo(email);
    }

    @Test
    public void givenBusProvider_whenfindById_theReturnBusProvider()
    {
        when(busProviderRepository.findById(1L)).thenReturn(Optional.of(busProvider));
        when(modelMapper.map(busProvider,BusProviderDto.class)).thenReturn(busProviderDto);

        BusProviderDto busProviderDto1=busProviderService.findById(1L);

        assertThat(busProviderDto1).isNotNull();
        assertThat(busProviderDto1.getEmailId()).isEqualTo("test@gmail.com");

    }
}
