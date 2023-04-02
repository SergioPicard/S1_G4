package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingModelRepository extends JpaRepository<BookingModel,Integer> {
}
