package com.example.AgenciaTurismo.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservaAuto")
public class CarsBookingRequestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false)
    private String userName;

    //RELACIONES
    @OneToOne
    private CarsModel bookingCars;

    @OneToOne
    private  PaymentMethodModel paymentMethodDto;


}
