package com.example.Bus_Ticket_Booking.Controller;

import com.example.Bus_Ticket_Booking.Controller.PassengerController;
import com.example.Bus_Ticket_Booking.Dto.BusesDto;
import com.example.Bus_Ticket_Booking.Dto.PassengerDto;
import com.example.Bus_Ticket_Booking.Service.BusProviderService;
import com.example.Bus_Ticket_Booking.Service.BusesService;
import com.example.Bus_Ticket_Booking.Service.PassengerService;
import com.example.Bus_Ticket_Booking.Service.TicketBookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PassengerController.class)
public class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private PassengerService passengerService;
    @MockBean private BusProviderService busProviderService;
    @MockBean private BusesService busesService;
    @MockBean private TicketBookingService ticketBookingService;
    @MockBean private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "user@example.com", roles = "PASSENGER")
    public void whenGetSearch_thenShowBusSearchResults() throws Exception {
        // Arrange
        String email = "user@example.com";
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setEmailId(email);

        List<BusesDto> busesDtos = Collections.singletonList(new BusesDto());

        when(passengerService.findByEmailid(email)).thenReturn(passengerDto);
        when(busesService.findByRoute("from", "to")).thenReturn(busesDtos);

        // Act & Assert
        mockMvc.perform(get("/passenger/search")
                        .param("form", "from")
                        .param("to", "to"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("passenger"))
                .andExpect(model().attributeExists("buses"))
                .andExpect(view().name("Passenger/afterBusSearch"));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = "PASSENGER")
    public void whenPostSaveTheBooking_thenRedirectToDashboard() throws Exception {
        // Arrange
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setId(1L);
        when(passengerService.findByEmailid("user@example.com")).thenReturn(passengerDto);

        // Act & Assert
        mockMvc.perform(post("/passenger/saveTheBooking")
                        .param("busProviderId", "2")
                        .param("busesId", "3")
                        .param("bookedDate", "2025-06-08")
                        .with(csrf())) // <-- Add this line!
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/passenger/dashboard"));
    }
}

