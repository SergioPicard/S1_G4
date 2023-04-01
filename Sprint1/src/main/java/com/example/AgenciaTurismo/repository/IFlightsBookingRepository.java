package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.FlightResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFlightsBookingRepository extends JpaRepository<FlightResponseModel,Integer> {
}
