package com.example.AgenciaTurismo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelAvailableDto {
    private String codigoHotel;
    private String nombre;
    private String lugar;
    private String tipoHabitacion;
    private Double precioNoche;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDate disponibleDesde;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDate disponibleHasta;
    private Boolean reservado;

}
