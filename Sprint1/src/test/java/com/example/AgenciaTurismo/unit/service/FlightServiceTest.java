package com.example.AgenciaTurismo.unit.service;

import com.example.AgenciaTurismo.dto.response.FlightReservationResDto;
import com.example.AgenciaTurismo.models.FlightReservationResModel;
import com.example.AgenciaTurismo.models.FlightResponseModel;
import com.example.AgenciaTurismo.repository.IFlightReservationResRepository;
import com.example.AgenciaTurismo.repository.IFlightsBookingRepository;
import com.example.AgenciaTurismo.service.classes.FlightsService;
import com.example.AgenciaTurismo.util.ConsultasNuevasFuncionalidades;
import com.example.AgenciaTurismo.util.FlightResponseDtoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.xmlunit.util.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    IFlightReservationResRepository flightReservationResRepository;

    @Mock
    IFlightsBookingRepository flightsBookingRepository;

    @InjectMocks
    FlightsService flightsService;

    ModelMapper mapper = new ModelMapper();

    //****************** TEST NUEVAS FUNCIONALIDADES SPRINT 3 INDIVIDUAL ****************************

    @Test
    @DisplayName("Buscar reservas por destino")
    public void findByDestinationTest(){
        //arrange
        List<FlightReservationResDto> expected = List.of(FlightResponseDtoFactory.flightReservationRes());
        var model = expected.stream().map(
                reserva -> mapper.map(reserva, FlightReservationResModel.class)
        ).collect(Collectors.toList());

        String destino = "Puerto Iguazú";

        //act
        Mockito.when(flightReservationResRepository.findByDestination(destino)).thenReturn(model);
        var result = flightsService.findByDestination(destino);

        //assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    @DisplayName("Exception Buscar reservas por destino")
    public void findByNoDestinationTest(){
        //arrange
        List<FlightReservationResModel> model = List.of();
        String destino = "Puerto Iguazú";

        //act && assert
        Mockito.when(flightReservationResRepository.findByDestination(destino)).thenReturn(model);

        Assertions.assertThrows(RuntimeException.class, ()-> flightsService
                .findByDestination(destino));
    }

    @Test
    @DisplayName("Consulta Total recaudado por venta de vuelo")
    public void getTotalForFlightTest(){
        //arrange
        List<Map<String,Object>> expected = ConsultasNuevasFuncionalidades.listSold();

        //act
        Mockito.when(flightsBookingRepository.getTotalForFlight()).thenReturn(expected);
        var result = flightsService.getTotalForFlight();

        //assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    @DisplayName("Excepción Consulta Total recaudado por venta de vuelo")
    public void getTotalForFlightTestNosold(){
        //arrange
        List<Map<String,Object>> expected = List.of();

        //act && assert
        Mockito.when(flightsBookingRepository.getTotalForFlight()).thenReturn(expected);

        Assertions.assertThrows(RuntimeException.class,
                () -> flightsService.getTotalForFlight());
    }

    @Test
    @DisplayName("Búsqueda de reservas entre 2 fechas")
    public void flightsReservationsBetweenDateTest(){
        //arrange
        List<FlightReservationResDto> expected = List.of(FlightResponseDtoFactory.flightReservationRes2());

        var model = expected.stream().map(
                reserva -> mapper.map(reserva,FlightReservationResModel.class)
        ).collect(Collectors.toList());

        var fecha1 = LocalDate.of(2022,02,01);
        var fecha2 = LocalDate.of(2022,02,28);

        //act
        Mockito.when(flightReservationResRepository.findByDateFromAndDateTo(fecha1,fecha2)).thenReturn(model);
        var result = flightsService.findByDateFromAndDateTo(fecha1,fecha2);


        //assert
        Assertions.assertEquals(result,expected);
    }

    @Test
    @DisplayName("Exception búsqueda entre 2 fechas")
    public void flightsReservationsBetweenDateTestException(){
        //arrange
        List<FlightReservationResModel> list = List.of();

        var fecha1 = LocalDate.of(2022,02,01);
        var fecha2 = LocalDate.of(2022,02,28);

        Mockito.when(flightReservationResRepository.findByDateFromAndDateTo(fecha1,fecha2)).thenReturn(list);

        //act && assert
        Assertions.assertThrows(RuntimeException.class,
                () -> flightsService.findByDateFromAndDateTo(fecha1,fecha2));
    }

    /*
    @Test
    @DisplayName("Se buscan todos los vuelos - SERVICE")
    public void searchAllTest(){
        // arrange
        List<FlightsAvailableDto> expected = FlightAvailableDtoFactory.listFlights();

        // act
        Mockito.when(flightsRepository.findAll()).thenReturn(FlightAvailableDtoFactory.listFlights());
        var result = flightsService.getAllEntities();

        // assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    @DisplayName("Se filtran los vuelos, con fechas, destino y origen como parámetros - SERVICE")
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
    @DisplayName("Chequeo excepción no existe destino - SERVICE")
    public void filterFlightTestNonDestinyException(){
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
    @DisplayName("No hay vuelos disponibles excepción - SERVICE")
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
    @DisplayName("Se reserva el vuelo con DTO de booking como parámetro - SERVICE")
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
    @DisplayName("Excepción por fecha de salida posterior a la de llegada - SERVICE")
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
    @DisplayName("Excepción de origen inexistente - SERVICIO")
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
    @DisplayName("Excepción de fecha de ingreso igual a la fecha de salida - SERVICE")
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
    @DisplayName("Excepción por no disponibilidad en la fecha establecida")
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

   /* @Test
    @DisplayName("")
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

    }*/

    /*
    @Test
    @DisplayName("Excepción cantidad incorrecta de pasajeros - SERVICE")
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
    @DisplayName("Excepción sin pasajeros en booking - SERVICE")
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
    @DisplayName("Excepción tipo de asiento incorrecto - SERVICE")
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
    @DisplayName("Excepción origen inexistente en el booking - SERVICE")
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
    @DisplayName("Excepción fecha salida incorrecta en booking - SERVICE")
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
    @DisplayName("Excepción fecha de llegada es posterior a la de salida - SERVICE")
    public void bookingFlightDateFromIsAfterDatoToException(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponse();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDto();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        param.getFlightReservation().setDatoTo(LocalDate.of(2022,2,9));


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }

    @Test
    @DisplayName("Excepción fecha de llegada y salida iguales - SERVICE")
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
    @DisplayName("Excepción usuario vacio en booking - SERVICE")
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
    @DisplayName("Intereses por cuotas en tarjeta de crédito - SERVICE")
    public void bookingFlightInterestCreditCard(){
        // arrange

        FlightResponseDto expected = FlightResponseDtoFactory.getResponseCredit3();
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDtoCredit3();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        /*param.getPaymentMethodDto().setType("debitcard");
        param.getPaymentMethodDto().setDues(1);
        expected.setTotal(6500.0);*/
//        param.getPaymentMethodDto().setDues(3);
/*
        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);
        var result = flightsService.flightReservationResponse(param);

        // act && assert
        Assertions.assertEquals(expected, result);

    }

    @Test
    @DisplayName("Excepción cantidad de cuotas incorrectas con tarjeta de débito - SERVICE")
    public void bookingFlightTestWrongDuesDebitCardException(){
        // arrange
        FlightReservationReqDto param = FlightReservationReqFactory.getFlightReservationDto();
        FlightsAvailableDto available = FlightAvailableDtoFactory.getBapi();
        param.getPaymentMethodDto().setType("debitcard");


        Mockito.when(flightsRepository.findFlight(param.getFlightReservation().getFlightNumber(),
                param.getFlightReservation().getSeatType())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(VuelosException.class, ()-> flightsService
                .flightReservationResponse(param));

    }*/
}
