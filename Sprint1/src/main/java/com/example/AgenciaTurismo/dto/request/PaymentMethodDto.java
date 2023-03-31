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

    @NotNull
    private String type;
    @Positive
    @Size(max = 19, min = 16)
    private String number;
    @Digits(integer = 1, fraction = 0, message = "Debe ser un número entero.")
    @Max(6)
    private Integer dues;
}
