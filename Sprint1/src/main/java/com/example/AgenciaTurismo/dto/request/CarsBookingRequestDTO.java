package com.example.AgenciaTurismo.dto.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarsBookingRequestDTO {
    @Email(message = "Por favor ingrese un e-mail válido.")
    private String userName;

    private @Valid CarsDTO carsBooking;
}