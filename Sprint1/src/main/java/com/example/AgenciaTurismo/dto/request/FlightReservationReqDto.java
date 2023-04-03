package com.example.AgenciaTurismo.dto.request;

import com.example.AgenciaTurismo.dto.response.FlightReservationResDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlightReservationReqDto {
    //las validaciones para que no este vacio
    @Email(message = "Por favor ingrese un e-mail válido.")
    private String userName;
    private @Valid FlightReservationResDto flightReservation;
    private @Valid PaymentMethodDto paymentMethodDto;

}