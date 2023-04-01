package com.example.AgenciaTurismo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "reservaVuelo")
public class FlightReservationReqModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 20, nullable = false)
    private String userName;

    //RELACIONES
    @OneToOne
    private FlightReservationResModel flightReservation;
    @OneToOne
    private  PaymentMethodModel paymentMethodDto;
}
