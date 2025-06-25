package com.example.Bus_Ticket_Booking.Controller;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Dto.BusesDto;
import com.example.Bus_Ticket_Booking.Entity.Buses;
import com.example.Bus_Ticket_Booking.Entity.TicketBooking;
import com.example.Bus_Ticket_Booking.Service.BusProviderService;
import com.example.Bus_Ticket_Booking.Service.BusesService;
import com.example.Bus_Ticket_Booking.Service.PassengerService;
import com.example.Bus_Ticket_Booking.Service.TicketBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Bus Provider",
        description = "Endpoints for bus provider registration, dashboard, and bus management")
@Controller
@RequestMapping("/busProvider")
@AllArgsConstructor
public class BusProviderController
{
    private BusProviderService busProviderService;
    private PassengerService passengerService;
    private PasswordEncoder passwordEncoder;
    private BusesService busesService;
    private TicketBookingService ticketBookingService;

    @Operation(
            summary = "Show registration form for bus providers",
            responses = @ApiResponse(responseCode = "200", description = "Registration form page")
    )

    @GetMapping("/registration")
    public String Registration(Model model)
    {
        BusProviderDto busProviderDto=new BusProviderDto();
        model.addAttribute("busProvider",busProviderDto);
        return "Bus/busOperatorRegistration";
    }

    @Operation(
            summary = "Register a new bus provider"
    )
            @PostMapping("/saveRegisration")
    public String saveRegistration(@ModelAttribute("busProvider") BusProviderDto busProviderDto, BindingResult bindingResult,Model model)
    {
        if(busProviderService.findBusProviderByEmailId(busProviderDto.getEmailId())!=null)
        {
            bindingResult.rejectValue("emailId","email.exist","This email is already registered. Please use another email.");
        }
       else if(passengerService.findByEmailid(busProviderDto.getEmailId())!=null)
        {
            bindingResult.rejectValue("emailId","email.exist","This email is already registered in customer. Please use another email.");
        }
        if(bindingResult.hasErrors())
        {
            return "Bus/busOperatorRegistration";
        }

        busProviderDto.setPassword(passwordEncoder.encode(busProviderDto.getPassword()));
        busProviderService.saveBusProvider(busProviderDto);

        return "redirect:/login?success";
    }


    @Operation(
            summary = "Show bus provider dashboard",
            responses = @ApiResponse(responseCode = "200", description = "Dashboard page")
    )
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication,Model model)
    {
        String email=authentication.getName();
        BusProviderDto busProviderDto=busProviderService.findBusProviderByEmailId(email);
        model.addAttribute("busprovder",busProviderDto);

        List<BusesDto> busesDtos=busesService.findBusesByBusProviderEmailId(email);
        model.addAttribute("buses",busesDtos);

        return "Bus/busproviderDashboard";
    }

    @Operation(
            summary = "Show add bus form",
            responses = @ApiResponse(responseCode = "200", description = "Add bus form page")
    )
    @GetMapping("/addbus")
    public String addBus(Authentication authentication,Model model)
    {
        String email=authentication.getName();
        BusProviderDto busProviderDto=busProviderService.findBusProviderByEmailId(email);
        model.addAttribute("busprovider",busProviderDto);
        BusesDto busesDto=new BusesDto();
        busesDto.setBusProvider_id(busProviderDto.getId());
        model.addAttribute("busdto",busesDto);

        return "Bus/addBus";
    }

    @PostMapping("/Savethebuses")
    public String SaveTHeBuses(@ModelAttribute("busdto") BusesDto busesDto, Authentication authentication)
    {
//        String email=authentication.getName();
//        BusProviderDto busProviderDto=busProviderService.findBusProviderByEmailId(email);
//
//        // Set the busProvider ID
//        busesDto.setBusProvider_id(busProviderDto.getId());

        // Save the bus
//        busesService.saveBueses(busesDto);
//
//        return "redirect:/busProvider/dashboard";
        busesService.saveBueses(busesDto);
        return "redirect:/busProvider/dashboard";

    }

    @Operation(
            summary = "Delete a bus by ID"
    )
    @GetMapping("/deleteTheBus/{id}")
    public String deleteTheBus(@PathVariable("id") Long id)
    {
        busesService.deleteBusById(id);
        return "redirect:/busProvider/dashboard";
    }

    @GetMapping("/upadteTheBusData/{id}")
    public String upadteTheBusData(@PathVariable("id") Long id,Model model)
    {
        if (id == null) {
            // Handle error, redirect, or show message
            return "redirect:/busProvider/dashboard?error=invalid_id";
        }
        BusesDto busesDto=busesService.findyById(id);

        model.addAttribute("oldbus",busesDto);

        return "Bus/update_bus";
    }
    @Operation(
            summary = "Save updated bus details"
    )
    @PostMapping("/savetheupdatebus")
    public String savetheupdatebus(@ModelAttribute("oldbus") BusesDto busesDto)
    {
        busesService.updateTheBus(busesDto);
        return "redirect:/busProvider/dashboard";
    }

    @Operation(
            summary = "Show date entry form for viewing tickets of a bus")

    @GetMapping("/viewBusTickets/{busId}")
    public String showDateEntryForm(@PathVariable Long busId, Model model) {
        model.addAttribute("busId", busId);
        return "Bus/dateEntryForm";
    }

    @Operation(
            summary = "Show tickets for a bus on a specific date")

    @PostMapping("/viewBusTickets")
    public String showTicketsForDate(@RequestParam Long busId,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedDate,
                                     Model model) {
        Buses bus = busesService.findById(busId);
        model.addAttribute("bus", bus);

        List<TicketBooking> tickets = ticketBookingService.findByBusIdAndBookedDate(busId, bookedDate);
        model.addAttribute("tickets", tickets);
        model.addAttribute("bookedDate", bookedDate);

        return "Bus/busTicketsByDate";
    }
}
