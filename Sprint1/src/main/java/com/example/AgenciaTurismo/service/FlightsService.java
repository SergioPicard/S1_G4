package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.request.FlightReservationReqDto;
import com.example.AgenciaTurismo.dto.response.*;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.repository.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public FlightResponseDto flightReservationResponse(FlightReservationReqDto flightReservationReqDto){
        FlightResponseDto response = new FlightResponseDto();

        //NUEVA RESPONSE RESERVA - DATOS SIN MEDIOS DE PAGO
        FlightReservationResDto booking = new FlightReservationResDto();

        //BUSQUEDA DEL VUELO POR CODIGO PASADO EN EL REQUEST.
        FlightModel bookedFlight = flightsRepository.findFlight(flightReservationReqDto.getFlightReservation().getFlightNumber(), flightReservationReqDto.getFlightReservation().getSeatType());

        booking.setFlightNumber(flightReservationReqDto.getFlightReservation().getFlightNumber());
        booking.setOrigin(flightReservationReqDto.getFlightReservation().getOrigin());
        booking.setSeats(flightReservationReqDto.getFlightReservation().getSeats());
        booking.setPeople(flightReservationReqDto.getFlightReservation().getPeople());
        booking.setDateFrom(flightReservationReqDto.getFlightReservation().getDateFrom());
        booking.setDatoTo(flightReservationReqDto.getFlightReservation().getDatoTo());
        booking.setDestination(flightReservationReqDto.getFlightReservation().getDestination());
        booking.setSeatType(flightReservationReqDto.getFlightReservation().getSeatType());


        //CALCULO DEL TOTAL DE LA COMPRA
        int tiempo = flightReservationReqDto.getFlightReservation().getDatoTo().getDayOfYear() - flightReservationReqDto.getFlightReservation().getDateFrom().getDayOfYear();
        System.out.println(tiempo);

        Double total = bookedFlight.getPrecioPersona() * tiempo;
        //Double total = bookedFlight.getPrecioPersona() * flightReservationReqDto.getFlightReservation().getSeats();




        //SETEO DEL RESPONSE
        response.setFlightReservation(booking);
        response.setUserName(flightReservationReqDto.getUserName());
        response.setStatus(new StatusCodeDto(200,"Reserva Satisfactoria"));
        response.setTotal(total);

        return response;
    }



}
