package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
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



}
