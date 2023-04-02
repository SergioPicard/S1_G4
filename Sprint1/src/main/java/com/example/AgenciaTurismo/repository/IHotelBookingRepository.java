package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.BookingRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHotelBookingRepository extends JpaRepository<BookingRequestModel,Integer> {
}
