package com.example.Bus_Ticket_Booking.Repository;

import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Entity.Buses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BusesRepositoryTest {

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
    private BusesRepository busesRepository;
    @Autowired
    private BusProviderRepository busProviderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private BusProvider busProvider;
    private Buses buses;

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
    }

    @Test
    public  void givenBuses_whenSaveBuses_thenReturnBuses()
    {
        //given (data was taken form the setup() )

        //when

        Buses savedBuses=busesRepository.save(buses);

        //then
        assertThat(savedBuses).isNotNull();
        assertThat(savedBuses.getBusProvider().getEmailId()).isEqualTo("test@gmail.com");
    }

    @Test
    public void givenBuses_whenFindBusesByBusProviderEmailId_thenReturnBuses()
    {
        //given
        Buses savedBuses=busesRepository.save(buses);
        BusProvider busProvider1=busProviderRepository.save(busProvider);

        //when
        List<Buses> allbues=busesRepository.findByBusProviderId(busProvider1.getId());

        //then

        assertThat(allbues).isNotEmpty();
        assertThat(allbues)
                .isNotEmpty()
                .allSatisfy(buses ->
                        assertThat(buses.getBusProvider().getId()).isEqualTo(busProvider1.getId())
                );



    }

    @Test
    public void givenBuses_whenDeleteBusById_thenNothing()
    {
        // given

        Buses savedbuses=busesRepository.save(buses);

        //when

        busesRepository.deleteById(savedbuses.getId());
    }


    @Test
    public void givenBusesId_whenFindBusesById_thenReturnBuses()
    {
        //given

        Buses savedbuses=busesRepository.save(buses);
        //when
        Buses oldbus=busesRepository.findById(savedbuses.getId()).orElseThrow(()->new RuntimeException("not buses"));

        //then
        assertThat(oldbus).isNotNull();
        assertThat(oldbus.getId()).isEqualTo(savedbuses.getId());
    }


}
