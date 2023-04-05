package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.FlightResponseModel;
import com.example.AgenciaTurismo.models.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IFlightsBookingRepository extends JpaRepository<FlightResponseModel,Integer> {

    //********************* MÉTODOS ACTIVIDAD INDIVIDUAL SPRINT 3 ********************************

    //CALCULAR TOTAL RECAUDADO POR VUELO
    @Query("SELECT new map(datoReserva.flightNumber as Vuelo, SUM(reserva.total) as TotalRecaudación) FROM FlightResponseModel as reserva INNER JOIN reserva.flightReservationResModel as datoReserva GROUP BY Vuelo")
    List<Map<String, Object>> getTotalForFlight();

}
