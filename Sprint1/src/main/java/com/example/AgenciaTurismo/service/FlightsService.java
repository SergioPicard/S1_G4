package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.request.FlightReservationDto;
import com.example.AgenciaTurismo.dto.response.*;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.HotelModel;
import com.example.AgenciaTurismo.repository.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class FlightsService {

    @Autowired
    FlightsRepository flightsRepository;

    public List<FlightsAvailableDto> searchAll(){
        return flightsRepository.findAll();
    }

    public List<FlightsAvailableDto> filterFlights(LocalDate fechaIda, LocalDate fechaVuelta, String origen, String destino){

        List<FlightsAvailableDto> flightsAvailable = flightsRepository.filterFlightRep(fechaIda, fechaVuelta, origen, destino);

        if(flightsAvailable.isEmpty()){
            throw new RuntimeException("No se encontraron vuelos disponibles en este periodo de tiempo y en el destino indicado :(.");
        }

        return flightsAvailable;
    }

    public FlightResponseDto flightReservationResponse(FlightReservationDto flightReservationDto){
        FlightResponseDto response = new FlightResponseDto();

        //NUEVA RESPONSE RESERVA - DATOS SIN MEDIOS DE PAGO
        FlightReservationResDto booking = new FlightReservationResDto();

        //BUSQUEDA DEL VUELO POR CODIGO PASADO EN EL REQUEST.
        FlightModel bookedFlight = flightsRepository.findFlight(flightReservationDto.getFlightReservation().getFlightNumber(), flightReservationDto.getFlightReservation().getSeatType());

        booking.setFlightNumber(flightReservationDto.getFlightReservation().getFlightNumber());
        booking.setOrigin(flightReservationDto.getFlightReservation().getOrigin());
        booking.setSeats(flightReservationDto.getFlightReservation().getSeats());
        booking.setPeople(flightReservationDto.getFlightReservation().getPeople());
        booking.setDateFrom(flightReservationDto.getFlightReservation().getDateFrom());
        booking.setDatoTo(flightReservationDto.getFlightReservation().getDatoTo());
        booking.setDestination(flightReservationDto.getFlightReservation().getDestination());
        booking.setSeatType(flightReservationDto.getFlightReservation().getSeatType());


        //CALCULO DEL TOTAL DE LA COMPRA
        Double total = bookedFlight.getPrecioPersona() * flightReservationDto.getFlightReservation().getSeats();


        //SETEO DEL RESPONSE
        response.setFlightReservation(booking);
        response.setUserName(flightReservationDto.getUserName());
        response.setStatus(new StatusCodeDto(200,"Reserva Satisfactoria"));
        response.setTotal(total);

        return response;
    }



}
