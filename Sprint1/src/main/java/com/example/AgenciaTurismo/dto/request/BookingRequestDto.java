package com.example.AgenciaTurismo.dto.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingRequestDto {

    @Email(message = "Flaco, pone un email.")
    private String userName;
    private @Valid BookingDto booking;

}

