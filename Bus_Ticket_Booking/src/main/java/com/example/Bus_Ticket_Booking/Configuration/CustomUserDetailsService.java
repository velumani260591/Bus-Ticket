package com.example.Bus_Ticket_Booking.Configuration;

import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Entity.Passenger;
import com.example.Bus_Ticket_Booking.Repository.BusProviderRepository;
import com.example.Bus_Ticket_Booking.Repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private BusProviderRepository busProviderRepository;

    @Override
    public UserDetails loadUserByUsername(String emailid) throws UsernameNotFoundException {

        Passenger passenger=passengerRepository.findByEmailId(emailid);
        if(passenger!=null)
        {
            return new org.springframework.security.core.userdetails.User(
                    passenger.getEmailId(),
                    passenger.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_PASSENGER"))
            );
        }

        BusProvider busProvider=busProviderRepository.findByEmailId(emailid);
        if(busProvider!=null)
        {
            return new org.springframework.security.core.userdetails.User(
                    busProvider.getEmailId(),
                    busProvider.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_BUSPROVIDER"))
            );
        }
        throw new UsernameNotFoundException("User not found");
    }
}
