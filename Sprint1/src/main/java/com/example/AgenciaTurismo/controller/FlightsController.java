package com.example.AgenciaTurismo.controller;


import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.request.BookingDto;
import com.example.AgenciaTurismo.dto.request.FlightReservationReqDto;
import com.example.AgenciaTurismo.dto.response.BookingResDto;
import com.example.AgenciaTurismo.dto.response.FlightReservationResDto;
import com.example.AgenciaTurismo.dto.response.FlightResponseDto;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.FlightReservationResModel;
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
    public  ResponseEntity<MessageDTO> editFlights(@RequestParam String flightNumber,
                                                  @RequestBody FlightModel flightModel){

        return  ResponseEntity.ok(
                flightsService.editEntity(flightNumber, flightModel));
    }

    @GetMapping("/flight-reservations")
    public ResponseEntity<List<FlightResponseDto>> findAllBookings(){
        return ResponseEntity.ok(flightsService.getAllBookings());
    }

    @PutMapping("/flight-reservation/{id}")
    public ResponseEntity<MessageDTO> updateBookingById(@PathVariable Integer id,
                                                        @RequestBody @Valid FlightResponseDto bookingDto){
        return ResponseEntity.ok(
                flightsService.updateBookingByID(id, bookingDto)
        );
    }

    @GetMapping("/flight/findBySeat")
    public List<FlightsAvailableDto> findBySeat(@RequestParam("destination") String destination,
                                                @RequestParam("seat") String seat) {
        return flightsService.findByDestinationAndSeat(destination, seat);

    }


    @GetMapping("/flight/findByLowerPrice")
    public List<FlightsAvailableDto> filterByLowerPrice(){
        return flightsService.findByLowerPrice();
    }
}
