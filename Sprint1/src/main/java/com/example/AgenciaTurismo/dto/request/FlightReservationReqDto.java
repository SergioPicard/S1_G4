package com.example.AgenciaTurismo.dto.request;

import com.example.AgenciaTurismo.dto.response.FlightReservationResDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FlightReservationReqDto {
    @Email(message = "Por favor ingrese un e-mail válido.")
    private String userName;
    private @Valid FlightReservationResDto flightReservation;
    private @Valid PaymentMethodDto paymentMethodDto;

}