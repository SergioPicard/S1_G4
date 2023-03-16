package com.example.AgenciaTurismo.util;

import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;

import java.time.LocalDate;

public class HotelAvailableDtoFactory {
    public static HotelAvailableDto cataratasHotel(){
        return HotelAvailableDto.builder()
                .codigoHotel("CH-0002")
                .nombre("Cataratas Hotel")
                .lugar("Puerto Iguazú")
                .tipoHabitacion("Doble")
                .precioNoche(6300.0)
                .disponibleDesde(LocalDate.of(2022,02,10))
                .disponibleHasta(LocalDate.of(2022,03,20))
                .reservado(false)
                .build();
    }

    public static HotelAvailableDto cataratasHotel2(){
        return HotelAvailableDto.builder()
                .codigoHotel("CH-0003")
                .nombre("Cataratas Hotel 2")
                .lugar("Puerto Iguazú")
                .tipoHabitacion("Triple")
                .precioNoche(8200.0)
                .disponibleDesde(LocalDate.of(2022,02,10))
                .disponibleHasta(LocalDate.of(2022,03,23))
                .reservado(false)
                .build();
    }

    public static HotelAvailableDto BristolHotel(){
        return HotelAvailableDto.builder()
                .codigoHotel("HB-0001")
                .nombre("Hotel Bristol")
                .lugar("Buenos Aires")
                .tipoHabitacion("Single")
                .precioNoche(5435.0)
                .disponibleDesde(LocalDate.of(2022,02,10))
                .disponibleHasta(LocalDate.of(2022,03,19))
                .reservado(false)
                .build();
    }

}
