package com.example.AgenciaTurismo.controller;


import com.example.AgenciaTurismo.service.classes.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@Validated
@RequestMapping("/api/v1/")
public class FlightsController {
    @Autowired
    FlightsService flightsService;



    /*@GetMapping("/flights")
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
    public FlightResponseDto booking(@RequestBody @Valid FlightReservationReqDto flightReservationReqDto){

        return flightsService.flightReservationResponse(flightReservationReqDto);
    }*/

}
