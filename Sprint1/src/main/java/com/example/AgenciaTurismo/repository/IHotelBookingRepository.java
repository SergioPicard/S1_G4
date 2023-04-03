package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.BookingModel;
import com.example.AgenciaTurismo.models.BookingRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IHotelBookingRepository extends JpaRepository<BookingRequestModel,Integer> {
}
