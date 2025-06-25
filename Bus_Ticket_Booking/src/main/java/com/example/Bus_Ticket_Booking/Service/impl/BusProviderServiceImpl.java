package com.example.Bus_Ticket_Booking.Service.impl;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Repository.BusProviderRepository;
import com.example.Bus_Ticket_Booking.Service.BusProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Tag(
        name = "it work for only the bus provider"
)
@Service
@AllArgsConstructor
public class BusProviderServiceImpl implements BusProviderService {

    private BusProviderRepository busProviderRepository;
    private ModelMapper modelMapper;

    @Operation(
            summary = "it save the new bus operator"
    )
    @Override
    public BusProviderDto saveBusProvider(BusProviderDto busProviderDto) {
        BusProvider busProvider=modelMapper.map(busProviderDto,BusProvider.class);

        BusProvider savedBusprovider=busProviderRepository.save(busProvider);

        return modelMapper.map(savedBusprovider,BusProviderDto.class);
    }

    @Operation(
            summary = "it find the bus operator with their emailId"
    )
    @Override
    public BusProviderDto findBusProviderByEmailId(String emailId) {

        BusProvider busProvider=busProviderRepository.findByEmailId(emailId);

        if(busProvider==null)
        {
            return null;
        }
        return modelMapper.map(busProvider,BusProviderDto.class);
    }

    @Operation(
            summary = "it get bus operator by using id"
    )
    @Override
    public BusProviderDto findById(Long id) {

        BusProvider busProvider=busProviderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No busProvider not found"));

        return modelMapper.map(busProvider,BusProviderDto.class);
    }
}
