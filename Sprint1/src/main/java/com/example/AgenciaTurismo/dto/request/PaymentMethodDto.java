package com.example.AgenciaTurismo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentMethodDto {
    private String type;

    private String number;


    @Digits(integer = 1, fraction = 0, message = "Debe ser un número entero.")
    @Max(6)
    private Integer dues;
}
