package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.FlightReservationResModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface IFlightReservationResRepository extends JpaRepository<FlightReservationResModel,Integer> {
    List<FlightReservationResModel> findByFlightNumber(String codigo);

    @Query("SELECT new map(b.flightModel.id AS id_vuelo, SUM(SIZE(b.people)) AS cant_personas) FROM FlightReservationResModel AS b GROUP BY b.flightModel.id")
    List<Map<String, Number>> getIdFlightPeopleAmount();

}
