package com.example.AgenciaTurismo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightResponseDto {
    private String userName;
    private Double total;
    private FlightReservationResDto flightReservation;
    private StatusCodeDto status;
}
