package com.example.Bus_Ticket_Booking.Repository;

import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BusProviderRepositoryTest {
    @TestConfiguration
   static class test
    {
        @Bean
        public PasswordEncoder passwordEncoder()
        {
            return new BCryptPasswordEncoder();
        }
    }


    @Autowired
    private BusProviderRepository busProviderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private BusProvider busProvider;


    @BeforeEach
    public void setUp()
    {
        busProvider=new BusProvider();
        busProvider.setName("tests");
        busProvider.setEmailId("test@gmail.com");
        busProvider.setPassword(passwordEncoder.encode("test"));
        busProvider.setPhoneNumber(1234567890L);
    }


    @Test
    public void  givenBusProvider_whenSaveBusProvider_ThenReturnBusProvider()
    {
        // given (taken the data form setup

        //when

        BusProvider savedBusprovider=busProviderRepository.save(busProvider);
        //then

        assertThat(savedBusprovider).isNotNull();
    }

    @Test
    public void givenBusProvider_whenFindByEmailId_thenReturnBusProvider()
    {
        //given
        String email="test@gmail.com";
        busProviderRepository.save(busProvider);

        //when

        BusProvider olddata=busProviderRepository.findByEmailId(email);

        //then
        assertThat(olddata).isNotNull();
        assertThat(olddata.getEmailId()).isEqualTo(email);
    }

    @Test
    public void givenBusProvider_whenFindById_thenReturnBusProvider()
    {
        //given
//        Long id=1L;
        BusProvider saved=busProviderRepository.save(busProvider);

        //when

        BusProvider oldBusProvider=busProviderRepository.findById(saved.getId()).orElseThrow(()->new RuntimeException("no bus provder"));

        //then
        assertThat(oldBusProvider).isNotNull();
        assertThat(oldBusProvider.getId()).isEqualTo(saved.getId());
    }
}
