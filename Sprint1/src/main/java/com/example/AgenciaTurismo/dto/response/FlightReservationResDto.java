package com.example.AgenciaTurismo.dto.response;

import com.example.AgenciaTurismo.dto.request.PaymentMethodDto;
import com.example.AgenciaTurismo.dto.request.PeopleDto;
import com.example.AgenciaTurismo.models.FlightModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlightReservationResDto {
    //mensajes de las validaciones?
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "es_ES", timezone = "America/New_York", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, without = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private LocalDate dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "es_ES", timezone = "America/New_York", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, without = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private LocalDate datoTo;
    @NotBlank(message = "El campo origen(origin) no debe estar vacío")
    private String origin;
    @NotBlank(message = "El campo destino(destination) no debe estar vacío")
    private String destination;
    @NotBlank(message = "El campo de numero de vuelo(flightNumber) no debe estar vacío")
    private String flightNumber;
    @Positive(message = "La cantidad de personas debe ser un valor numérico.")
    @NotNull(message = "El campo cantidad de asientos(seats) no debe estar vacío")
    private Integer seats;
    @NotBlank(message = "El campo tipo de asiento(seatType) no debe estar vacío")
    private String seatType;
    private @Valid List<PeopleDto> people;

}
