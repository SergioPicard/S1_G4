package com.example.AgenciaTurismo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "hotel")

public class HotelModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String codigoHotel;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 30, nullable = false)
    private String lugar;

    @Column(length = 30, nullable = false)
    private String tipoHabitacion;

    @Column(length = 9, nullable = false)
    private Double precioNoche;

    @Column()
    private LocalDate disponibleDesde;

    @Column()
    private LocalDate disponibleHasta;

    @Column()
    private Boolean reservado;

}
