package com.example.AgenciaTurismo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.OptBoolean;
>>>>>>> sprint3
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class HotelAvailableDto {
    private String codigoHotel;
    private String nombre;
    private String lugar;
    private String tipoHabitacion;
    private Double precioNoche;
<<<<<<< HEAD
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDate disponibleDesde;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
=======
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_ES", timezone = "America/New_York", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, without = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private LocalDate disponibleDesde;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_ES", timezone = "America/New_York", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, without = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
>>>>>>> sprint3
    private LocalDate disponibleHasta;
    private Boolean reservado;

}
