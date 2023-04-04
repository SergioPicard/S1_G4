package com.example.AgenciaTurismo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarsDTO {
    @NotEmpty(message = "El campo está vacio.")
    private String codigoAuto;
    @NotBlank(message = "El campo está vacio.")
    private String modelo;
    @NotBlank(message = "El campo está vacio.")
    private String lugar;
    private Double precioPorDia;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "es_ES", timezone = "America/New_York", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, without = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private LocalDate disponibleDesde;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "es_ES", timezone = "America/New_York", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, without = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private LocalDate disponibleHasta;

    @NotNull//un objeto no puede estar vacio
    private @Valid PaymentMethodDto paymentMethod;

}
