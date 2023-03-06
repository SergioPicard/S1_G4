package com.example.AgenciaTurismo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightReservationDto {
    private LocalDate dateFrom;
    private LocalDate datoTo;
    private String origin;
    private String destination;
    private String flightNumber;
    private Integer seats;
    private String seatType;
    private List<PeopleDto> people;
    private PaymentMethodDto paymentMethod;
}