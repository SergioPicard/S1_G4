package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.HotelModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FlightsRepository {

    List<FlightModel> flights = new ArrayList<>();

    List<FlightsAvailableDto> flightsAvailable = new ArrayList<>();

    public FlightsRepository(){
        flights.add(new FlightModel("BAPI-1235","Buenos Aires","Puerto Iguazú","Economy",6500.0, LocalDate.of(2022,02,10),LocalDate.of(2022,02,15)));
        flights.add(new FlightModel("PIBA-1420","Puerto Iguazú","Bogotá","Business",43200.0, LocalDate.of(2022,02,10),LocalDate.of(2022,02,20)));
        flights.add(new FlightModel("PIBA-1420","Puerto Iguazú","Bogotá","Economy",25735.0, LocalDate.of(2022,02,10),LocalDate.of(2022,02,21)));
        flights.add(new FlightModel("BATU-5536","Buenos Aires","Tucumán","Economy",7320.0, LocalDate.of(2022,02,10),LocalDate.of(2022,02,17)));
        flights.add(new FlightModel("TUPI-3369","Tucumán","Puerto Iguazú","Economy",5400.0, LocalDate.of(2022,01,02),LocalDate.of(2022,01,16)));
        flights.add(new FlightModel("TUPI-3369","Tucumán","Puerto Iguazú","Business",12530.0, LocalDate.of(2022,02,12),LocalDate.of(2022,02,23)));
        flights.add(new FlightModel("BOCA-4213","Bogotá","Cartagena","Economy",8000.0, LocalDate.of(2022,01,23),LocalDate.of(2022,02,05)));
        flights.add(new FlightModel("CAME-0321","Cartagena","Medellín","Economy",7800.0, LocalDate.of(2022,01,23),LocalDate.of(2022,01,31)));
        flights.add(new FlightModel("BOBA-6567","Bogotá","Buenos Aires","Business",57000.0, LocalDate.of(2022,02,15),LocalDate.of(2022,02,28)));
        flights.add(new FlightModel("BOBA-6567","Bogotá","Buenos Aires","Economy",39860.0, LocalDate.of(2022,03,01),LocalDate.of(2022,03,14)));
        flights.add(new FlightModel("BOME-4442","Bogotá","Medellín","Economy",11000.0, LocalDate.of(2022,02,10),LocalDate.of(2022,02,24)));
        flights.add(new FlightModel("MEPI-9986","Medellín","Puerto Iguazú","Business",41640.0, LocalDate.of(2022,04,17),LocalDate.of(2022,05,02)));

        for (FlightModel flight : flights) {
            flightsAvailable.add(new FlightsAvailableDto(flight.getNroVuelo(),flight.getOrigen(),
                    flight.getDestino(),flight.getTipoAsiento(),flight.getPrecioPersona(),flight.getFechaIda(),
                    flight.getFechaVuelta()));
        }
    }

    public List<FlightsAvailableDto> findAll(){
        return flightsAvailable;
    }

    public List<FlightsAvailableDto> filterFlightRep(LocalDate fechaIda, LocalDate fechaVuelta, String origen, String destino){

        return flightsAvailable.stream().filter(flight -> flight.getDestino().equalsIgnoreCase(destino) &&
                !flight.getFechaIda().isAfter(fechaIda) &&
                !flight.getFechaVuelta().isBefore(fechaVuelta) &&
                flight.getOrigen().equalsIgnoreCase(origen)).collect(Collectors.toList());
    }

    public FlightModel findFlight(String flightNumber, String seatType){
        return flights.stream().filter(flight -> flight.getNroVuelo().equalsIgnoreCase(flightNumber) &&
                flight.getTipoAsiento().equalsIgnoreCase(seatType)).findFirst().orElse(null);

    }
}
