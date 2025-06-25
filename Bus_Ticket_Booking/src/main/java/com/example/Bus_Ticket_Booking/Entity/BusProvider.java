package com.example.Bus_Ticket_Booking.Entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Tag(
        name = "it store the bus operator details"
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String emailId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long phoneNumber;



    @OneToMany(mappedBy = "busProvider",cascade =CascadeType.ALL)
    private List<Buses> buses;




}
