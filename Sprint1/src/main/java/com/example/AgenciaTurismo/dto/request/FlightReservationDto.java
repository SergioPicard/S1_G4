package com.example.AgenciaTurismo.dto.request;

import com.example.AgenciaTurismo.dto.response.FlightReservationResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightReservationDto {
    private String userName;
    private FlightReservationResDto flightReservation;

}