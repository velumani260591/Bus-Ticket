package com.example.Bus_Ticket_Booking.Service;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Dto.BusesDto;
import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Entity.Buses;
import com.example.Bus_Ticket_Booking.Repository.BusProviderRepository;
import com.example.Bus_Ticket_Booking.Repository.BusesRepository;
import com.example.Bus_Ticket_Booking.Service.impl.BusProviderServiceImpl;
import com.example.Bus_Ticket_Booking.Service.impl.BusesServiceImpl;
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
import org.springframework.ui.ModelMap;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusesServiceImplTest {

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
    private BusesRepository busesRepository;
    @Mock
    private BusProviderRepository busProviderRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private BusesServiceImpl busesService;


    private Buses buses;
    private BusesDto busesDto;
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

        buses=new Buses();

        buses.setBusName("test");
        buses.setBusNumber("xyz-1234");
        buses.setStartingPoint("salem");
        buses.setStartTime("11:00 Am");
        buses.setDestinationPoint("chennai");
        buses.setDestinationTime("6:00 Pm");
        buses.setPrice(800);
        buses.setBusProvider(busProvider);


        busesDto =new BusesDto();
        busesDto.setBusName("test");
        busesDto.setBusNumber("xyz-1234");
        busesDto.setStartingPoint("salem");
        busesDto.setStartTime("11:00 Am");
        busesDto.setDestinationPoint("chennai");
        busesDto.setDestinationTime("6:00 Pm");
        busesDto.setPrice(800);
        busesDto.setBusProvider_id(1L);
    }


    @Test
    public void givenBusesDetails_whenSaveTheDetails_thenReturnBuses() {
        when(busProviderRepository.findById(1L))
                .thenReturn(Optional.of(busProvider));
        when(modelMapper.map(busesDto, Buses.class)).thenReturn(buses);
        when(busesRepository.save(buses)).thenReturn(buses);
        when(modelMapper.map(buses, BusesDto.class)).thenReturn(busesDto);

        BusesDto busesDto1 = busesService.saveBueses(busesDto);


        assertThat(busesDto1).isNotNull();
        assertThat(busesDto1.getBusProvider_id()).isEqualTo(1L);
    }

    @Test
    public void givenBusId_whenDeleteBusById_thenRepositoryDeleteByIdCalled() {
        Long busId = 1L;

        // when
        busesService.deleteBusById(busId);

        // then
        verify(busesRepository).deleteById(busId);
    }


    @Test
    public void givenBusId_whenFindyById_thenReturnBusesDto() {
        // given
        buses.setId(1L);
        when(busesRepository.findById(1L)).thenReturn(Optional.of(buses));

        // when
        BusesDto result = busesService.findyById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getBusName()).isEqualTo("test");
        assertThat(result.getBusNumber()).isEqualTo("xyz-1234");
        assertThat(result.getStartingPoint()).isEqualTo("salem");
        assertThat(result.getDestinationPoint()).isEqualTo("chennai");

    }
}
