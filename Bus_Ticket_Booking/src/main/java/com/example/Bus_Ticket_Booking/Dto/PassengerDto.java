package com.example.Bus_Ticket_Booking.Dto;

import lombok.Data;

@Data
public class PassengerDto
{
    private Long id;
    private String name;
    private String emailId;
    private String password;
    private Long phoneNumber;
    private Long ticketId;
}
