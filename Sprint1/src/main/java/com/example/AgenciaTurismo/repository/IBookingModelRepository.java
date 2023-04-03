package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookingModelRepository extends JpaRepository<BookingModel,Integer> {
    List<BookingModel> findByHotelCode(String codigo);
}
