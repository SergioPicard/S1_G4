package com.example.AgenciaTurismo.unit.service;

import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.repository.IFlightsRepository;
import com.example.AgenciaTurismo.service.classes.FlightsService;
import com.example.AgenciaTurismo.util.FlightAvailableDtoFactory;
import com.example.AgenciaTurismo.util.FlightModelFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FlightsServiceTestNew {

    @Mock
    IFlightsRepository flightsRepository;

    @InjectMocks
    FlightsService flightsService;

    @Test
    void saveEntity() {
    }

    @Test
    void getAllEntities() {
    }

    @Test
    void getEntityById() {
    }

    @Test
    void deleteEntity() {
    }

    @Test
    @DisplayName("Se filtran los vuelos, con fechas, destino y origen como parámetros - SERVICE")
    void filterEntity() {
        // arrange
        LocalDate dateFrom = LocalDate.of(2022,02,10);
        LocalDate dateTo = LocalDate.of(2022,02,15);
        String origin = "Buenos Aires";
        String destination = "Puerto Iguazú";
        List<FlightsAvailableDto> expected = List.of(FlightAvailableDtoFactory.getBapi());

        // act
        Mockito.when(flightsRepository.findByFechaIdaAndFechaVueltaAndAndOrigenAndDestino(dateFrom, dateTo, origin, destination))
                .thenReturn(List.of(FlightModelFactory.getBapi()));

        var result = flightsService.filterEntity(dateFrom,dateTo,origin,destination);

        // assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    void flightReservationResponse() {
    }

    @Test
    void deleteFlightReservation() {
    }

    @Test
    void editEntity() {
    }

    @Test
    void getAllBookings() {
    }

    @Test
    void updateBookingByID() {
    }
}