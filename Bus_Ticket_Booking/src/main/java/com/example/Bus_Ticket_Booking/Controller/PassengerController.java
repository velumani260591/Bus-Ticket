package com.example.Bus_Ticket_Booking.Controller;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Dto.BusesDto;
import com.example.Bus_Ticket_Booking.Dto.PassengerDto;
import com.example.Bus_Ticket_Booking.Dto.TicketBookingDto;
import com.example.Bus_Ticket_Booking.Entity.Passenger;
import com.example.Bus_Ticket_Booking.Entity.TicketBooking;
import com.example.Bus_Ticket_Booking.Service.BusProviderService;
import com.example.Bus_Ticket_Booking.Service.BusesService;
import com.example.Bus_Ticket_Booking.Service.PassengerService;
import com.example.Bus_Ticket_Booking.Service.TicketBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Passenger",
        description = "Endpoints for passenger registration, dashboard, bus search, booking, and ticket management")

@Controller
@AllArgsConstructor
@RequestMapping("/passenger")
public class PassengerController
{
    private PassengerService passengerService;
    private BusProviderService busProviderService;
    private BusesService busesService;
    private TicketBookingService ticketBookingService;


    private PasswordEncoder passwordEncoder;


    @GetMapping("/register")
    public String register(Model model)
    {
        PassengerDto passenger=new PassengerDto();
        model.addAttribute("passenger",passenger);
        return "Passenger/passengerRegistration";
    }

    @Operation(
            summary = "Register a new passenger"
    )
    @PostMapping("/savePassenger")
    public String savePassenger(@ModelAttribute("passenger") PassengerDto passengerDto, BindingResult bindingResult, Model model)
    {
        if(passengerService.findByEmailid(passengerDto.getEmailId())!=null)
        {
            bindingResult.rejectValue("emailId","email.exist","This email is already registered. Please use another email.");
        }
       else if(busProviderService.findBusProviderByEmailId(passengerDto.getEmailId())!=null)
        {
            bindingResult.rejectValue("emailId","email.exist","This email is already registered in Bus PProvider ares. Please use another email.");
        }

        if (bindingResult.hasErrors())
        {
            return "Passenger/passengerRegistration";
        }

        passengerDto.setPassword(passwordEncoder.encode(passengerDto.getPassword()));
        passengerService.savepassnger(passengerDto);
        return "redirect:/login?success";

    }

    @Operation(
            summary = "Show passenger dashboard"
    )
    @GetMapping("/dashboard")
    public String Dashboard(Authentication authentication,Model model)
    {
        String email=authentication.getName();

        PassengerDto passengerDto=passengerService.findByEmailid(email);
        model.addAttribute("passenger",passengerDto);
        return "Passenger/passengerdashboard";
    }

    @Operation(
            summary = "Search buses by route")
    @GetMapping("/search")
    public String serachbus(@RequestParam("form") String form,
                            @RequestParam("to") String to,
                            Authentication authentication,
                            Model model
                            )
    {
        String email=authentication.getName();

        PassengerDto passengerDto=passengerService.findByEmailid(email);
        model.addAttribute("passenger",passengerDto);

        List<BusesDto> busesDtos=busesService.findByRoute(form,to);

        model.addAttribute("buses",busesDtos);
        return "Passenger/afterBusSearch";
    }

    @GetMapping("/getBookingDetails/{id}")
    public String getBookingDetails(@PathVariable("id") Long id, Model model)
    {
        //Fetch the bus details
        BusesDto busesDto=busesService.findyById(id);
        model.addAttribute("bus",busesDto);

        //Fetch the bus provider details
        BusProviderDto busProviderDto=busProviderService.findById(busesDto.getBusProvider_id());
        model.addAttribute("provider", busProviderDto);

        //prepare booking Dto
        TicketBookingDto ticketBookingDto=new TicketBookingDto();
        ticketBookingDto.setBusesId(busesDto.getId());
        ticketBookingDto.setBusProviderId(busesDto.getBusProvider_id());
        model.addAttribute("ticket", ticketBookingDto);


        return "ticket/ticketBookingPage";
    }

    // Handle booking form submission
    @PostMapping("/saveTheBooking")
    public String saveTheBooking(@ModelAttribute("ticket") TicketBookingDto ticketBookingDto,
                                 Authentication authentication,Model model)
    {
        //Fetch the passenger

        String emailId=authentication.getName();
        PassengerDto passengerDto=passengerService.findByEmailid(emailId);

        ticketBookingDto.setPassengerId(passengerDto.getId());

        //fec
        ticketBookingDto.setBusProviderId(ticketBookingDto.getBusProviderId());

        ticketBookingDto.setBusesId(ticketBookingDto.getBusesId());
        // Save booking
        ticketBookingService.saveBooking(ticketBookingDto);
        return "redirect:/passenger/dashboard";
    }


    @Operation(
            summary = "View passenger ticket history")

    @GetMapping("/ticketHistory")
    public String tickerHistory(Authentication authentication,Model model)
    {
        String email=authentication.getName();
        // Fetch the passenger using the login details
        PassengerDto passengerDto=passengerService.findByEmailid(email);
        model.addAttribute("passenger",passengerDto);
        //Fetch all tickets were match by passenger id

//        List<TicketBookingDto> allTickets=ticketBookingService.getTicketsByPassengerId(passengerDto.getId());
//        model.addAttribute("tickets",allTickets);
        List<TicketBooking> tickets = ticketBookingService.getTicketsByPassengerId(passengerDto.getId());
        model.addAttribute("tickets", tickets);
        return "ticket/ticketHistory";
    }

    @Operation(
            summary = "View most recently booked ticket")

    @GetMapping("/bookedticket")
    public String bookedTicket(Authentication authentication,Model model)
    {
        String email=authentication.getName();
        // Fetch the passenger using the login details
        PassengerDto passengerDto=passengerService.findByEmailid(email);

        List<TicketBooking> ticketBookingList=ticketBookingService.getTicketsByPassengerId(passengerDto.getId());

        TicketBooking ticket=ticketBookingList.get(ticketBookingList.size()-1);
        model.addAttribute("ticket",ticket);
        return "ticket/bookedticket";

    }

    @GetMapping("/delete/{id}")
    public String deleteTicker(@PathVariable("id") Long id)
    {
        ticketBookingService.deleteTicket(id);
        return "redirect:/passenger/dashboard";
    }


}
