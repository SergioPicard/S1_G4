package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.FlightReservationResModel;
import com.example.AgenciaTurismo.models.FlightResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IFlightReservationResRepository extends JpaRepository<FlightReservationResModel,Integer> {
    List<FlightReservationResModel> findByFlightNumber(String codigo);


    //********************* MÉTODOS ACTIVIDAD INDIVIDUAL SPRINT 3 ********************************

    //BÚSQUEDA DE RESERVAS A UN DESTINO EN PARTICULAR
    List<FlightReservationResModel> findByDestination(String destino);

    //BÚSQUEDA DE RESERVAS ENTRE FECHAS DETERMINADAS
    @Query("FROM FlightReservationResModel as reserva WHERE reserva.dateFrom BETWEEN :fecha1 AND :fecha2")
    List<FlightReservationResModel> findByDateFromAndDateTo(@Param("fecha1") LocalDate fecha1, @Param("fecha2")LocalDate fecha2);

}
