package com.example.AgenciaTurismo.unit.service;

import com.example.AgenciaTurismo.dto.request.BookingRequestDto;
import com.example.AgenciaTurismo.dto.request.FlightReservationReqDto;
import com.example.AgenciaTurismo.dto.response.BookingResponseDto;
import com.example.AgenciaTurismo.dto.response.FlightResponseDto;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.exceptions.SinHotelesException;
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
}