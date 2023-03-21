package com.example.AgenciaTurismo.unit.service;

import com.example.AgenciaTurismo.dto.request.BookingDto;
import com.example.AgenciaTurismo.dto.request.BookingRequestDto;
import com.example.AgenciaTurismo.dto.request.FlightReservationReqDto;
import com.example.AgenciaTurismo.dto.response.*;
import com.example.AgenciaTurismo.exceptions.SinHotelesException;
import com.example.AgenciaTurismo.exceptions.VuelosException;
import com.example.AgenciaTurismo.repository.IFlightsRepository;
import com.example.AgenciaTurismo.repository.IHotelesRepository;
import com.example.AgenciaTurismo.service.FlightsService;
import com.example.AgenciaTurismo.service.HotelesService;
import com.example.AgenciaTurismo.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HotelesServiceTest {

    @Mock
    IHotelesRepository hotelesRepository;

    @InjectMocks
    HotelesService hotelesService;

    @Test
    void searchAll() {
        // arrange
        List<HotelAvailableDto> expected = HotelAvailableDtoFactory.listHotels();

        // act
        Mockito.when(hotelesRepository.findAll()).thenReturn(HotelAvailableDtoFactory.listHotels());
        var result = hotelesService.searchAll();

        // assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void filterHotels() {
        // arrange
        LocalDate fechaDesde = LocalDate.of(2022,02,10);
        LocalDate fechaHasta = LocalDate.of(2022,03,20);
        String destino = "Puerto Iguazú";
        List<HotelAvailableDto> expected = List.of(HotelAvailableDtoFactory.cataratasHotel());
        System.out.println(expected);

        // act
        Mockito.when(hotelesRepository.findAll())
                .thenReturn(HotelAvailableDtoFactory.listHotels());
        Mockito.when(hotelesRepository.filterHotelsRep(fechaDesde,fechaHasta,destino))
                .thenReturn(List.of(HotelAvailableDtoFactory.cataratasHotel()));


        var result = hotelesService.filterHotels(fechaDesde,fechaHasta,destino);
        System.out.println(result);

        // assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void filterHotelNotFound(){
        // arrange
        LocalDate desde = LocalDate.of(2022,02,10);
        LocalDate hasta = LocalDate.of(2022,03,19);
        String destino = "Córdoba";

        Mockito.when(hotelesRepository.findAll())
                .thenReturn(List.of());

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .filterHotels(desde,hasta,destino));
    }

    @Test
    void bookingResponse() {

        //  arrange
        BookingResponseDto expected = HotelResponseDtoFactory.getResponse();
        BookingRequestDto hotel = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto hotelAvailable = HotelAvailableDtoFactory.cataratasHotel();

        // act
        Mockito.when(hotelesRepository.findHotel(hotel.getBooking().getHotelCode())).thenReturn(hotelAvailable);
        var result = hotelesService.bookingResponse(hotel);

        System.out.println(result);
        System.out.println(expected);

        // assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void filterHotelTestBeforeDate(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,10);
        LocalDate fechaVuelta = LocalDate.of(2022,02,9);
        String destino = "Puerto Iguazú";

        Mockito.when(hotelesRepository.findAll()).thenReturn(HotelAvailableDtoFactory.listHotels());

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .filterHotels(fechaIda,fechaVuelta,destino));

    }
    @Test
    public void filterHotelTestEqualDate(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,10);
        LocalDate fechaVuelta = LocalDate.of(2022,02,10);
        String destino = "Puerto Iguazú";

        Mockito.when(hotelesRepository.findAll()).thenReturn(HotelAvailableDtoFactory.listHotels());

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .filterHotels(fechaIda,fechaVuelta,destino));

    }


    @Test
    public void filterFlightTestNonAvailableException(){
        // arrange
        LocalDate fechaIda = LocalDate.of(2022,02,10);
        LocalDate fechaVuelta = LocalDate.of(2022,02,15);
        String destino = "Puerto Iguazú";

        Mockito.when(hotelesRepository.findAll()).thenReturn(HotelAvailableDtoFactory.listHotels());
        Mockito.when(hotelesRepository.filterHotelsRep(fechaIda, fechaVuelta, destino)).thenReturn(List.of());

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .filterHotels(fechaIda,fechaVuelta,destino));

    }

    @Test
    public void bookingHotelsTwoPeopleException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationPeopleDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.BristolHotel();


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }
    @Test
    public void bookingHotelsThreePeopleException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationPeopleDtoThree();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsFourPeopleException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationPeopleDtoFour();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel2();


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsFivePeopleException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationPeopleDtoFive();
        HotelAvailableDto available = HotelAvailableDtoFactory.centralPlaza();


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsNotEqualPeopleException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.getBooking().setPeopleAmount(2);


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotels0PeopleException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.getBooking().setPeopleAmount(0);


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsNotEqualRoomTypeException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.getBooking().setRoomType("Triple");


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsNotEqualDestinationException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.getBooking().setDestination("Santa Fe");


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsNotFindDateException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.getBooking().setDateFrom(LocalDate.of(2022,04,10));
        param.getBooking().setDatoTo(LocalDate.of(2022,05,10));


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsDateToException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.getBooking().setDateFrom(LocalDate.of(2022,04,10));
        param.getBooking().setDatoTo(LocalDate.of(2022,03,10));


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsDateFromException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.getBooking().setDateFrom(LocalDate.of(2022,04,10));
        param.getBooking().setDatoTo(LocalDate.of(2022,04,10));


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsUserEmptyException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.setUserName("");

        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsDebitCardException(){
        // arrange

        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.getBooking().getPaymentMethod().setType("debitcard");

        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        // act && assert
        Assertions.assertThrows(SinHotelesException.class, ()-> hotelesService
                .bookingResponse(param));

    }

    @Test
    public void bookingHotelsCreditCard3DuesException(){
        // arrange
        BookingResponseDto expected = HotelResponseDtoFactory.getResponse();
        BookingRequestDto param = HotelReservationReqFactory.getResponseReservationDto();
        HotelAvailableDto available = HotelAvailableDtoFactory.cataratasHotel();
        param.getBooking().getPaymentMethod().setDues(3);
        expected.setTotal(251370.0);
        expected.getStatus().setMessage("Reserva Satisfactoria. Por utilizar TC tiene un recargo del 5%. Su recargo es de: $11970.0");


        Mockito.when(hotelesRepository.findHotel(param.getBooking().getHotelCode())).thenReturn(available);

        var result = hotelesService.bookingResponse(param);
        // act && assert

        Assertions.assertEquals(expected, result);


    }







}