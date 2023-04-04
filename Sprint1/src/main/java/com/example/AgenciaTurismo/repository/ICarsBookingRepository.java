package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.CarsReservationResModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarsBookingRepository extends JpaRepository<CarsReservationResModel,Integer> {
}