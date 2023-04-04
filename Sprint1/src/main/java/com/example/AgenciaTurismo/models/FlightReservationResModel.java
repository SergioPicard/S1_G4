package com.example.AgenciaTurismo.models;

import com.example.AgenciaTurismo.dto.response.FlightReservationResDto;
import com.example.AgenciaTurismo.dto.response.StatusCodeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "datosReservaVuelo")

public class FlightReservationResModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column()
    private LocalDate dateFrom;
    @Column()
    private LocalDate datoTo;
    @Column(length = 20, nullable = false)
    private String origin;
    @Column(length = 20, nullable = false)
    private String destination;
    @Column(length = 10, nullable = false)
    private String flightNumber;
    @Column(length = 2, nullable = false)
    private Integer seats;
    @Column(length = 20, nullable = false)
    private String seatType;

    //RELACIONES
    @OneToMany(cascade = CascadeType.ALL)
    private List<PeopleModel> people;

    @OneToOne
    private FlightModel flightModel;


}
