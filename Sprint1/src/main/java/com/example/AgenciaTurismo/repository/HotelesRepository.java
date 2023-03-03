package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.models.HotelModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HotelesRepository {

    List<HotelModel> hotels = new ArrayList<>();
    List<HotelAvailableDto> hotelsAvailable = new ArrayList<>();

    public HotelesRepository(){

        hotels.add(new HotelModel("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", 6300.0, LocalDate.of(2022, 2, 10), LocalDate.of(2022, 3, 20), false));
        hotels.add(new HotelModel("CH-0003", "Cataratas Hotel 2", "Puerto Iguazú", "Triple", 8200.0, LocalDate.of(2022, 2, 10), LocalDate.of(2022, 3, 23), false));
        hotels.add(new HotelModel("HB-0001", "Hotel Bristol", "Buenos Aires", "Single", 5435.0, LocalDate.of(2022, 2, 10), LocalDate.of(2022, 3, 19), false));
        hotels.add(new HotelModel("BH-0002", "Hotel Bristol 2", "Buenos Aires", "Doble", 7200.0, LocalDate.of(2022, 2, 12), LocalDate.of(2022, 4, 17), false));
        hotels.add(new HotelModel("SH-0002", "Sheraton", "Tucumán", "Doble", 5790.0, LocalDate.of(2022, 4, 17), LocalDate.of(2022, 5, 23), false));
        hotels.add(new HotelModel("SH-0001", "Sheraton 2", "Tucumán", "Single", 4150.0, LocalDate.of(2022, 1, 2), LocalDate.of(2022, 2, 19), false));
        hotels.add(new HotelModel("SE-0001", "Selina", "Bogotá", "Single", 3900.0, LocalDate.of(2022, 1, 23), LocalDate.of(2022, 11, 23), false));
        hotels.add(new HotelModel("SE-0002", "Selina 2", "Bogotá", "Doble", 5840.0, LocalDate.of(2022, 1, 23), LocalDate.of(2022, 10, 15), false));
        hotels.add(new HotelModel("EC-0003", "El Campín", "Bogotá", "Triple", 7020.0, LocalDate.of(2022, 2, 15), LocalDate.of(2022, 3, 27), false));
        hotels.add(new HotelModel("CP-0004", "Central Plaza", "Medellín", "Múltiple", 8600.0, LocalDate.of(2022, 3, 1), LocalDate.of(2022, 4, 17), false));
        hotels.add(new HotelModel("CP-0002", "Central Plaza 2", "Medellín", "Doble", 6400.0, LocalDate.of(2022, 2, 10), LocalDate.of(2022, 3, 20), false));
        hotels.add(new HotelModel("BG-0004", "Bocagrande", "Cartagena", "Múltiple", 9370.0, LocalDate.of(2022,4,17), LocalDate.of(2022,6,22), false));

        for (HotelModel hotel : hotels) {
            hotelsAvailable.add(new HotelAvailableDto(hotel.getNombre(),hotel.getLugar(),hotel.getTipoHabitacion(),
                    hotel.getPrecioNoche(),hotel.getDisponibleDesde(),hotel.getDisponibleHasta(),
                    hotel.getReservado()));
        }
    }

    public List<HotelAvailableDto> findAll(){

        return hotelsAvailable;
    }

    public List<HotelAvailableDto> filterHotelsRep(LocalDate dateFrom, LocalDate dateTo, String destination){

        return hotelsAvailable.stream().filter(hotel -> hotel.getLugar().equals(destination) &&
                !hotel.getDisponibleDesde().isAfter(dateFrom) &&
                !hotel.getDisponibleHasta().isBefore(dateTo) &&
                !hotel.getReservado()).collect(Collectors.toList());
    }

}
