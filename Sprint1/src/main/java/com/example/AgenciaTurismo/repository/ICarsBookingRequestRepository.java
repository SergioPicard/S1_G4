package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.CarsBookingRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarsBookingRequestRepository  extends JpaRepository<CarsBookingRequestModel,Integer> {
}
