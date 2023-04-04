package com.example.AgenciaTurismo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter


public class CarsAvailableDTO {

    private String codigoAuto;

    private String modelo;

    private String lugar;

    private Double precioPorDia;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate disponibleDesde;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate disponibleHasta;

    private Boolean reservado;
}

