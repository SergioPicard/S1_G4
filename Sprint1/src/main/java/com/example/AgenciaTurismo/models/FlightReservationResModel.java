package com.example.AgenciaTurismo.models;

import com.example.AgenciaTurismo.dto.response.FlightReservationResDto;
import com.example.AgenciaTurismo.dto.response.StatusCodeDto;
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
@Table(name = "datosReservaVuelo")

public class FlightReservationResModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //RELACIONES
    @OneToOne(cascade = CascadeType.PERSIST)
    private FlightModel flightModel;
    @OneToMany
    private List<PeopleModel> people;



        /*@Column()
    private LocalDate dateFrom;
    @Column()
    private LocalDate datoTo;
    @Column(length = 20, nullable = false)
    private String origin;
    @Column(length = 20, nullable = false)
    private String destination;
    @Column(length = 10, nullable = false)
    private String flightNumber;
    @Column(length = 3, nullable = false)
    private Integer seats;
    @Column(length = 10, nullable = false)
    private String seatType;*/

}
