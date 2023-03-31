package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IHotelesRepository extends JpaRepository<HotelModel,Integer> {

    List<HotelModel> findByCodigoHotel(String codigo);

/*    List<HotelModel> dataBase();

    List<HotelAvailableDto> findAll();

    List<HotelAvailableDto> filterHotelsRep(LocalDate dateFrom, LocalDate dateTo, String destination);

    HotelAvailableDto findHotel(String code);*/
}
