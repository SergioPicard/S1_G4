package com.example.AgenciaTurismo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
=======
import com.fasterxml.jackson.annotation.OptBoolean;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
>>>>>>> sprint3
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class BookingDto {
<<<<<<< HEAD
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate datoTo;
    @NotBlank(message = "Debe ingresar un destino")
=======
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "es_ES", timezone = "America/New_York", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, without = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private LocalDate dateFrom;

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "es_ES", timezone = "America/New_York", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, without = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private LocalDate datoTo;

    @NotBlank(message = "El destino no puede estar vacío.")
>>>>>>> sprint3
    private String destination;

    @NotEmpty(message = "El código del hotel no puede estar vacío.")
    private String hotelCode;

    @Positive(message = "La cantidad de personas debe ser un valor numérico.")
    private Integer peopleAmount;

    @NotBlank(message = "El tipo de habitación no puede estar vacío.")
    private String roomType;

    @NotEmpty(message = "La lista de personas está vacía.")
    private List<@Valid PeopleDto> people;

    @NotNull//un objeto no puede estar vacio
    private @Valid PaymentMethodDto paymentMethod;
}

