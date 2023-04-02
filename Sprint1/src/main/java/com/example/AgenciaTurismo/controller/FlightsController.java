package com.example.AgenciaTurismo.controller;


import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.request.FlightReservationReqDto;
import com.example.AgenciaTurismo.dto.response.FlightResponseDto;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.HotelModel;
import com.example.AgenciaTurismo.service.classes.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@RestController
@Validated
@RequestMapping("/api/v1/")
public class FlightsController {
    @Autowired
    FlightsService flightsService;

    @GetMapping("/flights")
    public ResponseEntity<List<FlightsAvailableDto>> findAllFlights(){
        return ResponseEntity.ok(flightsService.getAllEntities());
    }

    @PostMapping("/flights/new")
    public ResponseEntity<FlightsAvailableDto> newHotel(@RequestBody FlightsAvailableDto flightsAvailableDto){
        return ResponseEntity.ok(
                flightsService.saveEntity(flightsAvailableDto)
        );
    }
    @DeleteMapping("/flights/{code}")
    public ResponseEntity<MessageDTO> deleteByIdFlight(@PathVariable String code) {
        return ResponseEntity.ok(
                flightsService.deleteEntity(code)
        );
    }

    @GetMapping("/flight")
    public List<FlightsAvailableDto> filterFlights(@RequestParam("dateFrom") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaIda,
                                                   @RequestParam("dateTo") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaVuelta,
                                                   @RequestParam("origin") String origen,
                                                   @RequestParam("destination") String destino){

        return flightsService.filterEntity(fechaIda, fechaVuelta, origen, destino);
    }
   @PostMapping("/flight-reservation/new")
    public ResponseEntity<MessageDTO> booking(@RequestBody  FlightReservationReqDto flightReservationReqDto){
        return ResponseEntity.ok(
                flightsService.flightReservationResponse(flightReservationReqDto));
    }

    @DeleteMapping("/flight-reservation/delete/{id}")
    public  ResponseEntity<MessageDTO> deleteFlightReservation(@PathVariable Integer id){
        return ResponseEntity.ok(
                flightsService.deleteFlightReservation(id));
    }

    @PutMapping("/flights/edit")
    public  ResponseEntity<MessageDTO> editFlights(@RequestParam String code,
                                                  @RequestBody FlightModel flightModel){

        return  ResponseEntity.ok(
                flightsService.editEntity(code, flightModel));
    }

}
