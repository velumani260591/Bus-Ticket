package com.example.Bus_Ticket_Booking.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(
        name = "loginpage area"
)
@Controller
public class AuthController
{

//    @Operation(
//            summary = "it open the login page"
//    )
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "success", required = false) String success,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid email or password.");
        }
        if (success != null) {
            model.addAttribute("successMsg", "Account was created successfully!!!");
        }
        return "loginPages";
    }

    @GetMapping("/register")
    public String register()
    {
        return "registerbuttons";
    }
}
