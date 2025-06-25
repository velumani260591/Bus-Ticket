package com.example.Bus_Ticket_Booking.Controller;

import com.example.Bus_Ticket_Booking.Controller.BusProviderController;
import com.example.Bus_Ticket_Booking.Entity.Buses;
import com.example.Bus_Ticket_Booking.Entity.TicketBooking;
import com.example.Bus_Ticket_Booking.Service.BusProviderService;
import com.example.Bus_Ticket_Booking.Service.BusesService;
import com.example.Bus_Ticket_Booking.Service.PassengerService;
import com.example.Bus_Ticket_Booking.Service.TicketBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BusProviderController.class)
public class BusProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private BusesService busesService;
    @MockBean private PasswordEncoder passwordEncoder;
    @MockBean
    private BusProviderService busProviderService;
    @MockBean
    private PassengerService passengerService;
    @MockBean
    private BusProviderController busProviderController;


    @MockBean private TicketBookingService ticketBookingService; // <-- Add this


    @BeforeEach
    void setup() {
        ViewResolver viewResolver = new InternalResourceViewResolver("/", ".html");
        mockMvc = MockMvcBuilders
                .standaloneSetup(busProviderController)
                .setViewResolvers(viewResolver)
                .build();
    }
    @Test
    @WithMockUser(username = "provider@example.com", roles = "BUSPROVIDER")
    public void whenPostSaveTheBuses_thenRedirectToDashboard() throws Exception {
        mockMvc.perform(post("/Savethebuses")
                        .param("busName", "TestBus")
                        .param("busNumber", "TN01AB1234")
                        .param("startingPoint", "Chennai")
                        .param("startTime", "10:00")
                        .param("destinationPoint", "Salem")
                        .param("destinationTime", "15:00")
                        .param("price", "500")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/busProvider/dashboard"));

        verify(busesService).saveBueses(any());
    }

    @Test
    @WithMockUser(username = "provider@example.com", roles = "BUSPROVIDER") // <-- Add this annotation

    public void whenGetViewBusTickets_thenShowDateEntryFormWithBusId() throws Exception {
        mockMvc.perform(get("/viewBusTickets/42"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("busId", 42L))
                .andExpect(view().name("Bus/dateEntryForm"));
    }




}
