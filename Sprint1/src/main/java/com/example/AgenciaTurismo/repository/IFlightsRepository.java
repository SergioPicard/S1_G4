package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.FlightModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IFlightsRepository extends JpaRepository<FlightModel,Integer> {

    List<FlightModel> findByNroVuelo(String codigo);

    List<FlightModel> findByFechaIdaAndFechaVueltaAndAndOrigenAndDestino
            (LocalDate dateFrom, LocalDate dateTo,String origen, String destination);

    FlightModel findByNroVueloAndTipoAsientoEquals(String code, String asiento);

    @Query("SELECT h FROM FlightModel h ORDER BY h.precioPersona ASC")
    List <FlightModel> findAllOrderByPrecioPersonaAsc();
}
