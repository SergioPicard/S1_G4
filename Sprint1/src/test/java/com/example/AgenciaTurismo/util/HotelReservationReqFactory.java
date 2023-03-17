package com.example.AgenciaTurismo.util;

import com.example.AgenciaTurismo.dto.request.BookingDto;
import com.example.AgenciaTurismo.dto.request.BookingRequestDto;
import com.example.AgenciaTurismo.dto.request.PaymentMethodDto;
import com.example.AgenciaTurismo.dto.request.PeopleDto;
import com.example.AgenciaTurismo.dto.response.BookingResDto;
import com.example.AgenciaTurismo.dto.response.BookingResponseDto;
import com.example.AgenciaTurismo.dto.response.StatusCodeDto;

import java.time.LocalDate;
import java.util.List;

public class HotelReservationReqFactory {

    public static BookingRequestDto getResponseReservationDto(){

        return BookingRequestDto.builder()
                .userName("lucianoefesta@hotmail.com")
                .booking(getBookingHotel())
                .build();
    }

    public static BookingDto getBookingHotel(){
        return BookingDto.builder()
                .dateFrom(LocalDate.of(2022, 02, 10))
                .datoTo(LocalDate.of(2022,03,20))
                .hotelCode("CH-0002")
                .destination("Puerto Iguazú")
                .roomType("DOBLE")
                .peopleAmount(1)
                .people(List.of(getPeople()))
                .paymentMethod(getPaymentMethod())
                .build();
    }

    public static PeopleDto getPeople(){
        return PeopleDto.builder()
                .dni("36365939")
                .email("lucianoefesta@hotmail.com")
                .birthDate(LocalDate.of(1992,01,24))
                .lastName("Festa")
                .name("Luciano")
                .build();
    }

    public static PaymentMethodDto getPaymentMethod(){
        return PaymentMethodDto.builder()
                .dues(6)
                .type("creditcard")
                .number("4545656598987878")
                .build();
    }

}
