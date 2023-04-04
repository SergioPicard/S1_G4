package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IHotelesRepository extends JpaRepository<HotelModel,Integer> {

    List<HotelModel> findByCodigoHotel(String codigo);

    //Busca y filtra los hoteles en base al precio maximo que uno quiere
    @Query("FROM HotelModel h WHERE h.precioNoche <= :precio AND h.reservado = false")
    List<HotelModel> findByPrecioNoche(@Param("precio")Double precio);

    List<HotelModel> findByDisponibleDesdeLessThanEqualAndDisponibleHastaGreaterThanEqualAndLugar
            (LocalDate dateFrom, LocalDate dateTo, String destination);

    HotelModel findBycodigoHotelAndTipoHabitacionEquals(String code, String habitacion);

}
