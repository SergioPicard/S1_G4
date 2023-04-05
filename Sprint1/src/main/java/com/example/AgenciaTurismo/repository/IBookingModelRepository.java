package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.models.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface IBookingModelRepository extends JpaRepository<BookingModel,Integer> {
    List<BookingModel> findByHotelCode(String codigo);

    @Query("SELECT new map(b.hotelModel.id AS id_hotel, (SIZE(b.people)) AS cant_personas) FROM BookingModel AS b")
    List<Map<String, Integer>> getIdHotelPeopleAmount();

}
