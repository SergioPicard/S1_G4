package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.request.BookingRequestDto;
import com.example.AgenciaTurismo.dto.response.BookingResponseDto;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class FlightsController {
    @Autowired
    FlightsService flightsService;

    @GetMapping("/flights")
    public List<FlightsAvailableDto> searchAllFlights(){
        return flightsService.searchAll();
    }

    @GetMapping("/flight")
    public List<FlightsAvailableDto> filterFlights(@RequestParam("dateFrom") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaIda,
                                                @RequestParam("dateTo") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaVuelta,
                                                @RequestParam("origin") String origen,
                                                @RequestParam("destination") String destino){

        return flightsService.filterFlights(fechaIda, fechaVuelta, origen, destino);
    }

    @PostMapping("/flight-reservation")
    public BookingFlightResponseDto booking(@RequestBody BookingRequestDto bookingRequest){

        return hotelesService.bookingResponse(bookingRequest);
    }

}
