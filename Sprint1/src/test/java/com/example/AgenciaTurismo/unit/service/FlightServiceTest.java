package com.example.AgenciaTurismo.unit.service;

import com.example.AgenciaTurismo.dto.request.FlightReservationReqDto;
import com.example.AgenciaTurismo.dto.response.FlightResponseDto;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.exceptions.VuelosException;
import com.example.AgenciaTurismo.repository.IFlightsRepository;
import com.example.AgenciaTurismo.service.FlightsService;
import com.example.AgenciaTurismo.service.IFlightsService;
import com.example.AgenciaTurismo.util.FlightAvailableDtoFactory;
import com.example.AgenciaTurismo.util.FlightReservationReqFactory;
import com.example.AgenciaTurismo.util.FlightResponseDtoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    IFlightsRepository flightsRepository;

    @InjectMocks
    FlightsService flightsService;

    @Test
    public void searchAllTest(){
        // arrange
        List<FlightsAvailableDto> expected = FlightAvailableDtoFactory.listFlights();

        // act
        Mockito.when(flightsRepository.findAll()).thenReturn(FlightAvailableDtoFactory.listFlights());
        var result = flightsService.searchAll();

        // assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void filterFlightsTest(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,10);
        LocalDate fechaVuelta = LocalDate.of(2022,02,15);
        String origen = "Buenos Aires";
        String destino = "Puerto Iguazú";
        List<FlightsAvailableDto> expected = List.of(FlightAvailableDtoFactory.getBapi());

        // act
        Mockito.when(flightsRepository.findAll()).thenReturn(FlightAvailableDtoFactory.listFlights());
        Mockito.when(flightsRepository.filterFlightRep(fechaIda,fechaVuelta,origen,destino))
                .thenReturn(List.of(FlightAvailableDtoFactory.getBapi()));

        var result = flightsService.filterFlights(fechaIda,fechaVuelta,origen,destino);

        // assert
        Assertions.assertEquals(expected,result);

    }

    @Test
    public void filterFlightTestException(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,10);
        LocalDate fechaVuelta = LocalDate.of(2022,02,15);
        String origen = "Buenos Aires";
        String destino = "Córdoba";

        // act && assert
        Assertions.assertThrows(RuntimeException.class, ()-> flightsService
                .filterFlights(fechaIda,fechaVuelta,origen,destino));

    }

    @Test
    public void filterFlightTestFlightException(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,10);
        LocalDate fechaVuelta = LocalDate.of(2022,02,15);
        String origen = "Buenos Aires";
        String destino = "Córdoba";

        Mockito.when(flightsRepository.findAll()).thenReturn(List.of());

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .filterFlights(fechaIda,fechaVuelta,origen,destino));

    }


    @Test
    public void flightReservationResponseTest(){
        //  arrange
        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto flight = FlightReservationReqFactory.getFlightReservationDto();
        FlightsAvailableDto flightAvalilable = FlightAvailableDtoFactory.getBapi();

        // act
        Mockito.when(flightsRepository.findFlight(flight.getFlightReservation().getFlightNumber(),
                flight.getFlightReservation().getSeatType())).thenReturn(flightAvalilable);
        var result = flightsService.flightReservationResponse(flight);

        // assert
        Assertions.assertEquals(expected,result);

    }
    @Test
    public void filterFlightTestWrongDateException(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,15);
        LocalDate fechaVuelta = LocalDate.of(2022,02,10);
        String origen = "Buenos Aires";
        String destino = "Puerto Iguazú";

        Mockito.when(flightsRepository.findAll()).thenReturn(FlightAvailableDtoFactory.listFlights());

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .filterFlights(fechaIda,fechaVuelta,origen,destino));

    }

    @Test
    public void filterFlightTestNotOriginException(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,10);
        LocalDate fechaVuelta = LocalDate.of(2022,02,15);
        String origen = "Santa Fe";
        String destino = "Puerto Iguazú";

        Mockito.when(flightsRepository.findAll()).thenReturn(FlightAvailableDtoFactory.listFlights());

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .filterFlights(fechaIda,fechaVuelta,origen,destino));

    }

    @Test
    public void filterFlightTestEqualDateException(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,10);
        LocalDate fechaVuelta = LocalDate.of(2022,02,10);
        String origen = "Buenos Aires";
        String destino = "Puerto Iguazú";

        Mockito.when(flightsRepository.findAll()).thenReturn(FlightAvailableDtoFactory.listFlights());

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .filterFlights(fechaIda,fechaVuelta,origen,destino));

    }

    @Test
    public void filterFlightTestNotDateAvailableException(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,01);
        LocalDate fechaVuelta = LocalDate.of(2022,02,05);
        String origen = "Buenos Aires";
        String destino = "Puerto Iguazú";

        Mockito.when(flightsRepository.findAll()).thenReturn(FlightAvailableDtoFactory.listFlights());

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .filterFlights(fechaIda,fechaVuelta,origen,destino));

    }

    @Test
    public void filterFlightTestNonAvailableException(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,10);
        LocalDate fechaVuelta = LocalDate.of(2022,02,15);
        String origen = "Buenos Aires";
        String destino = "Puerto Iguazú";

        Mockito.when(flightsRepository.findAll()).thenReturn(FlightAvailableDtoFactory.listFlights());
        Mockito.when(flightsRepository.filterFlightRep(fechaIda, fechaVuelta, origen, destino)).thenReturn(List.of());

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .filterFlights(fechaIda,fechaVuelta,origen,destino));

    }

    @Test
    public void bookingFlightTestWrongAmountOfPeopleException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getReservationWrong();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }


    @Test
    public void bookingFlightTest0PeopleException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDtoWrong();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }

    @Test
    public void bookingFlightTestWrongSeatTypeException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDtoWrong();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        param.getFlightReservation().setSeatType("Business");


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }

    @Test
    public void bookingFlightTestWrongOriginException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDtoWrong();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        param.getFlightReservation().setOrigin("Santa Fe");


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }

    @Test
    public void bookingFlightTestWrongDateException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDtoWrong();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        param.getFlightReservation().setDateFrom(LocalDate.of(2022, 02, 9 ));


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }

    @Test
    public void bookingFlightDateFromIsAfterDatoToException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDtoWrong();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        param.getFlightReservation().setDatoTo(LocalDate.of(2022,2,9));


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }

    @Test
    public void bookingFlightDateFromIsEqualDatoToException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDtoWrong();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        param.getFlightReservation().setDatoTo(LocalDate.of(2022,2,10));


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }

    @Test
    public void bookingFlightTestNonUserException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDtoWrong();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        param.setUserName("");


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }

    @Test
    public void bookingFlightWrongDuesException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDtoWrong();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        param.getPaymentMethodDto().setType("debitcard");
//        param.getPaymentMethodDto().setDues(3);


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }
}
