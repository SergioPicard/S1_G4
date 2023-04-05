package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IHotelesRepository extends JpaRepository<HotelModel,Integer> {

    List<HotelModel> findByCodigoHotel(String codigo);

    List<HotelModel> findByDisponibleDesdeLessThanEqualAndDisponibleHastaGreaterThanEqualAndLugar
            (LocalDate dateFrom, LocalDate dateTo, String destination);

    HotelModel findBycodigoHotelAndTipoHabitacionEquals(String code, String habitacion);

    List<HotelModel> findByTipoHabitacionEquals(String roomType);

    List<HotelModel> findByPrecioNocheLessThanEqual(Double price);

}
