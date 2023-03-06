package com.example.AgenciaTurismo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingFlightRequestDto {
    private String userName;
    private FlightReservationDto reservation;

}