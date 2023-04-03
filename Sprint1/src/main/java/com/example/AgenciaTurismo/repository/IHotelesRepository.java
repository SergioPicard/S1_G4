package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IHotelesRepository extends JpaRepository<HotelModel,Integer> {

    List<HotelModel> findByCodigoHotel(String codigo);



    List<HotelModel> findByDisponibleDesdeLessThanEqualAndDisponibleHastaGreaterThanEqualAndLugar
            (LocalDate dateFrom, LocalDate dateTo, String destination);

    HotelModel findBycodigoHotelAndTipoHabitacionEquals(String code, String habitacion);


    List<HotelModel> findByLugar(String destination) ;
    @Query("SELECT h FROM HotelModel h ORDER BY h.precioNoche ASC")
    List <HotelModel> findAllOrderByPrecioNocheAsc();

    List<HotelModel> findByNombreContainsIgnoreCase(String nameHotel);


/*    List<HotelModel> dataBase();

    List<HotelAvailableDto> findAll();

    List<HotelAvailableDto> filterHotelsRep(LocalDate dateFrom, LocalDate dateTo, String destination);

    HotelAvailableDto findHotel(String code);*/
}
