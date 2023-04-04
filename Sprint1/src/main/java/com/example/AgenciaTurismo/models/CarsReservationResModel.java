package com.example.AgenciaTurismo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "datosReservaAuto")
public class CarsReservationResModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column()
    private LocalDate dateFrom;
    @Column()
    private LocalDate datoTo;
    @Column(length = 20, nullable = false)
    private String origin;
    @Column(length = 10, nullable = false)
    private String carsCode;
    @Column(length = 2, nullable = false)
    private Integer peopleAmount;
    @Column(length = 10, nullable = false)
    private Double total;


    //RELACIONES
    @OneToOne
    private CarsModel carsModel;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<PeopleModel> people;
    @OneToOne
    private PaymentMethodModel paymentMethod;

}
