package com.example.AgenciaTurismo.dto.response;

import com.example.AgenciaTurismo.dto.request.PaymentMethodDto;
import com.example.AgenciaTurismo.models.PaymentMethodModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlightResponseDto {
    @NotNull(message = "El usuario(userName) no debe estar vacío.")
    private String userName;
    private Double total;
    private @Valid FlightReservationResDto flightReservation;
    private @Valid PaymentMethodDto paymentMethod;
}
