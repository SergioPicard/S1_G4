package com.example.AgenciaTurismo.util;

import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.models.HotelModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelAvailableDtoFactory {
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

    public static List<HotelModel> listHotelsForLowerPrice(){
        List<HotelModel> list = new ArrayList<>();
        list.add(BristolHotel());
        list.add(cataratasHotel());
        list.add(cataratasHotel2());
        list.add(centralPlaza());

        return list;
    }

    public static MessageDTO MessageDTOFactory(){

       return MessageDTO.builder()
                .message("El costo abonando con tarjeta de crédito (incluye recargo) para " +2+ " personas, durante "
                        + 10 + " días, en el hotel código: " + "CH-0002"
                        + ", es de: $" + 138600.0 + " realizado en "
                        + 6 + " cuotas, con un costo de c/u: $" + 23100.0)
                .name("COSTO SIMULACIÓN")
                .build();

    }

    public static HotelAvailableDto cataratasHotel1(){
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

    public static HotelAvailableDto cataratasHotel21(){
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

    public static HotelAvailableDto BristolHotel1(){
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

    public static HotelAvailableDto centralPlaza1(){
        return HotelAvailableDto.builder()
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

    public static List<HotelAvailableDto> listHotels1(){
        List<HotelAvailableDto> list = new ArrayList<>();
        list.add(cataratasHotel1());
        list.add(cataratasHotel21());
        list.add(BristolHotel1());
        list.add(centralPlaza1());

        return list;
    }

    public static List<HotelAvailableDto> listHotelsForLowerPrice1(){
        List<HotelAvailableDto> list = new ArrayList<>();
        list.add(BristolHotel1());
        list.add(cataratasHotel1());
        list.add(cataratasHotel21());
        list.add(centralPlaza1());

        return list;
    }


}
