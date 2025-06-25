package com.example.Bus_Ticket_Booking.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String redirectUrl="/login";
        if(authentication.getAuthorities().stream()
                .anyMatch(a->a.getAuthority().equals("ROLE_PASSENGER")))
        {
            redirectUrl="/passenger/dashboard";
        }

        else if(authentication.getAuthorities().stream()
                .anyMatch(a->a.getAuthority().equals("ROLE_BUSPROVIDER")))
        {
            redirectUrl="/busProvider/dashboard";
        }
        response.sendRedirect(redirectUrl);
    }
}
