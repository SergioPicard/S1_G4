package com.example.AgenciaTurismo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "payment_method")

public class PaymentMethodModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 40)
    private String type;
    @Column(length = 19)
    private String number;
    @Column(length = 2, nullable = false)
    private Integer dues;

}
