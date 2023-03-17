package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.exceptions.SinHotelesException;
import com.example.AgenciaTurismo.models.FlightModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class FlightsRepository implements IFlightsRepository{
    List<FlightsAvailableDto> flightsAvailable = new ArrayList<>();

    public FlightsRepository(){
        dataBase();
    }

    public List<FlightModel> dataBase(){
        List<FlightModel> vuelos = null;

        File file;
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule());
        TypeReference<List<FlightModel>> typeRef = new TypeReference<>() {};

        try {
            file = ResourceUtils.getFile("classpath:JSON/vuelos.json");
            vuelos = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert vuelos != null;
        for (FlightModel flight : vuelos) {
            flightsAvailable.add(new FlightsAvailableDto(flight.getNroVuelo(),flight.getOrigen(),
                    flight.getDestino(),flight.getTipoAsiento(),flight.getPrecioPersona(),flight.getFechaIda(),
                    flight.getFechaVuelta()));
        }

        return vuelos;
    }

    public List<FlightsAvailableDto> findAll(){
        return flightsAvailable;
    }

    public List<FlightsAvailableDto> filterFlightRep(LocalDate fechaIda, LocalDate fechaVuelta, String origen, String destino){
        List<FlightsAvailableDto> allFlights = findAll();
        List<FlightsAvailableDto> destinationStatus = allFlights.stream().filter(flight -> Objects.equals(flight.getDestino(), destino)).collect(Collectors.toList());
        List<FlightsAvailableDto> originStatus = allFlights.stream().filter(flight -> Objects.equals(flight.getOrigen(), origen)).collect(Collectors.toList());
        List<FlightsAvailableDto> dateEqualFromStatus = destinationStatus.stream().filter(flight -> flight.getFechaIda().equals(fechaIda)).collect(Collectors.toList());
        List<FlightsAvailableDto> dateEqualToStatus = destinationStatus.stream().filter(flight -> flight.getFechaVuelta().equals(fechaVuelta)).collect(Collectors.toList());

        // VALIDACION POR DESTINO
        if (destinationStatus.isEmpty()){
            throw new SinHotelesException("No se encontraron vuelos disponibles en esta fecha por el destino.");
        }

        // VALIDACION POR ORIGEN
        if (originStatus.isEmpty()){
            throw new SinHotelesException("No se encontraron vuelos disponibles en esta fecha por el origen.");
        }


        //VALIDACION POR FECHA
        if ( dateEqualFromStatus.isEmpty() && dateEqualToStatus.isEmpty()) {
            throw new SinHotelesException("No se encontraron vuelos disponibles en esta fecha.");
        }


        return flightsAvailable.stream().filter(flight -> flight.getDestino().equalsIgnoreCase(destino) &&
                !flight.getFechaIda().isAfter(fechaIda) &&
                !flight.getFechaVuelta().isBefore(fechaVuelta) &&
                flight.getOrigen().equalsIgnoreCase(origen)).collect(Collectors.toList());
    }

    public FlightsAvailableDto findFlight(String flightNumber, String seatType){
        return flightsAvailable.stream().filter(flight -> flight.getNroVuelo().equalsIgnoreCase(flightNumber) &&
                flight.getTipoAsiento().equalsIgnoreCase(seatType)).findFirst().orElse(null);

    }
}
