package com.example.AgenciaTurismo.dto.request;

import com.example.AgenciaTurismo.dto.response.FlightReservationResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlightReservationReqDto {
    @NotBlank
    private String userName;
    private @Valid FlightReservationResDto flightReservation;
    private @Valid PaymentMethodDto paymentMethodDto;

}