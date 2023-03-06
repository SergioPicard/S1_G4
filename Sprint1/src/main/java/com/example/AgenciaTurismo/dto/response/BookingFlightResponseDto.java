package com.example.AgenciaTurismo.dto.response;

import com.example.AgenciaTurismo.dto.request.FlightReservationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingFlightResponseDto {
    private String userName;
    private Double total;
    private FlightReservationDto booking;
    private StatusCodeDto status;
}
