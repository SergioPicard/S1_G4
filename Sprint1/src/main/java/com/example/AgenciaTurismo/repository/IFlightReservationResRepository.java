package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.FlightReservationResModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFlightReservationResRepository extends JpaRepository<FlightReservationResModel,Integer> {
    List<FlightReservationResModel> findByFlightNumber(String codigo);

}
