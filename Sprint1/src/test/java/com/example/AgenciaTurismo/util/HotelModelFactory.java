package com.example.AgenciaTurismo.util;

import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.HotelModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelModelFactory {
    public static HotelModel cataratasHotel(){
        return HotelModel.builder()
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

    public static HotelModel cataratasHotel2(){
        return HotelModel.builder()
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

    public static HotelModel BristolHotel(){
        return HotelModel.builder()
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

    public static HotelModel centralPlaza(){
        return HotelModel.builder()
                .codigoHotel("CP-0004")
                .nombre("Central Plaza")
                .lugar("Medellín")
                .tipoHabitacion("Múltiple")
                .precioNoche(8600.0)
                .disponibleDesde(LocalDate.of(2022,03,01))
                .disponibleHasta(LocalDate.of(2022,04,17))
                .reservado(false)
                .build();
    }

    public static List<HotelModel> listHotels(){
        List<HotelModel> list = new ArrayList<>();
        list.add(cataratasHotel());
        list.add(cataratasHotel2());
        list.add(BristolHotel());
        list.add(centralPlaza());

        return list;
    }

}
