package com.example.AgenciaTurismo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentMethodDto {
    private String type;
    private String number;
    private Integer dues;
}
