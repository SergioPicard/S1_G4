package com.example.AgenciaTurismo.dto.request;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentMethodDto {

    @NotNull(message = "El campo de tipo de tarjeta(type) no puede estar vació")
    private String type;
    @Positive
    @Size(max = 19, min = 16)
    @NotNull(message = "El campo de numero de tarjeta(number) no puede estar vació")
    private String number;
    @Digits(integer = 1, fraction = 0, message = "Debe ser un número entero.")
    @Max(6)
    @NotNull(message = "El campo de cuotas(dues) no puede estar vació")
    private Integer dues;
}
