package com.example.AgenciaTurismo.models;

import com.example.AgenciaTurismo.dto.response.FlightReservationResDto;
import com.example.AgenciaTurismo.dto.response.StatusCodeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "reservaVuelo")
public class FlightResponseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column()
    private String userName;
    @Column()
    private Double total;

    @OneToOne(cascade = CascadeType.ALL)
    private FlightReservationResModel flightReservationResModel;

    @OneToOne(cascade = CascadeType.ALL)
    private PaymentMethodModel paymentMethod;

}
