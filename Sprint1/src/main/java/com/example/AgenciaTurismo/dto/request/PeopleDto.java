package com.example.AgenciaTurismo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PeopleDto {
    @Size(max = 8)
    @NotBlank(message = "El campo de dni(dni) no puede estar vació")
    private String dni;
    @NotBlank(message = "El campo de nombre(name) no puede estar vació")
    private String name;
    @NotBlank(message = "El campo de apellido(lastName) no puede estar vació")
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_ES", timezone = "America/New_York", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, without = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    @NotNull(message = "El campo de fecha de nacimiento(birthDate) no puede estar vació")
    private LocalDate birthDate;
    @Email(message = "Por favor ingrese un e-mail válido")
    @NotNull(message = "El campo de mail(email) no puede estar vació")
    private String email;
}

