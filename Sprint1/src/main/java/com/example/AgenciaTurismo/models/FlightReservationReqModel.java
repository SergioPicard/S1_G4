package com.example.AgenciaTurismo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "reqReservaVuelo")
public class FlightReservationReqModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 20, nullable = false)
    private String userName;

    //RELACIONES
    @OneToOne(cascade = CascadeType.ALL)
    private FlightReservationResModel flightReservation;
    @OneToOne(cascade = CascadeType.ALL)
    private PaymentMethodModel paymentMethodDto;
}
