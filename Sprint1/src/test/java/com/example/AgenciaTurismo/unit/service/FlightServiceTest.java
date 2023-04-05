package com.example.AgenciaTurismo.unit.service;

import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.exceptions.CustomException;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.repository.IFlightsRepository;
import com.example.AgenciaTurismo.service.classes.FlightsService;
import com.example.AgenciaTurismo.util.FlightAvailableDtoFactory;
import com.example.AgenciaTurismo.util.FlightModelFactory;
import com.example.AgenciaTurismo.util.MessageDtoFactory;
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
class FlightServiceTest {

    @Mock
    IFlightsRepository flightsRepository;

    @InjectMocks
    FlightsService flightsService;

    @Test
    @DisplayName("Se guarda un nuevo Vuelo")
    void saveEntity() {
        // arrange
        FlightsAvailableDto body = FlightAvailableDtoFactory.getPiba();
        var expected = MessageDTO.builder()
                .message("Vuelo dado de alta correctamente.")
                .name("CREACIÓN")
                .build();
        FlightModel model = FlightModelFactory.getPiba();
        FlightModel entity = FlightModelFactory.gePibaNoId();

        // act
        Mockito.when(flightsRepository.save(entity)).thenReturn(model);

        var result = flightsService.saveEntity(body);
        // assert

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Se buscan todos los vuelos - SERVICE")
    void searchAllTest(){
        // arrange
        List<FlightsAvailableDto> expected = FlightAvailableDtoFactory.listFlights();

        // act
        Mockito.when(flightsRepository.findAll()).thenReturn(FlightModelFactory.listFlights());
        var result = flightsService.getAllEntities();

        // assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    @DisplayName("Se filtran los vuelos, con fechas, destino y origen como parámetros - SERVICE")
    void filterEntityTest(){
        // arrange
        LocalDate dateFrom = LocalDate.of(2022,2,10);
        LocalDate dateTo = LocalDate.of(2022, 2,15);
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
    @DisplayName("Chequeo excepción no existe destino - SERVICE")
    void filterFlightTestNonDestinyException(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,2,10);
        LocalDate fechaVuelta = LocalDate.of(2022,2,15);
        String origen = " ";
        String destino = " ";

        // act && assert
        Assertions.assertThrows(CustomException.class, ()-> flightsService
                .filterEntity(fechaIda,fechaVuelta,origen,destino));

    }

    @Test
    @DisplayName("Se buscan todas los vuelos con un precio menor o igual al definido")
    void findByPrecioPersonaLessThanEqualTest(){
        // arrange
        var param = (double) 10000;
        var expected = List.of(FlightAvailableDtoFactory.getBapi());

        // act
        Mockito.when(flightsRepository.findByPrecioPersonaLessThanEqual(param)).thenReturn(List.of(FlightModelFactory.getBapi()));

        var result = flightsService.findByPrecioPersonaLessThanEqual(param);
        // assert
        Assertions.assertEquals(expected, result);

    }

    @Test
    @DisplayName("Se prueba la excepcion al no encontrar vuelos con el precio")
    void findByPrecioPersonaLessThanEqualExceptionTest(){
        // arrange
        var param = (double) 100;

        // act and assert
        Mockito.when(flightsRepository.findByPrecioPersonaLessThanEqual(param)).thenReturn(List.of());

        Assertions.assertThrows(CustomException.class, ()-> flightsService
                .findByPrecioPersonaLessThanEqual(param));
    }

}
