package com.example.AgenciaTurismo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "vuelo")

public class FlightModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String nroVuelo;

    @Column(length = 30, nullable = false)
    private String origen;

    @Column(length = 30, nullable = false)
    private String destino;

    @Column(length = 30, nullable = false)
    private String tipoAsiento;

    @Column(length = 30, nullable = false)
    private Double precioPersona;

    @Column()
    private LocalDate fechaIda;

    @Column()
    private LocalDate fechaVuelta;


}
