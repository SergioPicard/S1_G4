package com.example.AgenciaTurismo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
// JPA

@Entity
@Table(name = "autos")
public class CarsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 30, nullable = false)
    private String codigoAuto;
    @Column(length = 30, nullable = false)
    private String modelo;
    @Column(length = 30, nullable = false)
    private String lugar;
    @Column(length = 30, nullable = false)
    private Double precioPorDia;
    @Column()
    private LocalDate disponibleDesde;
    @Column()
    private LocalDate disponibleHasta;
    @Column()
    private Boolean reservado;
}
