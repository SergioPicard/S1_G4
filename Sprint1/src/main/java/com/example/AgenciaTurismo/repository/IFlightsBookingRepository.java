package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.FlightResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IFlightsBookingRepository extends JpaRepository<FlightResponseModel,Integer> {
}
