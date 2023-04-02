package com.example.AgenciaTurismo.models;

import com.example.AgenciaTurismo.dto.request.PaymentMethodDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "datosReservaHotel")

public class BookingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    private LocalDate dateFrom;

    @Column()
    private LocalDate datoTo;

    @Column(length = 30, nullable = false)
    private String destination;

    @Column(length = 10, nullable = false)
    private String hotelCode;

    @Column(length = 2, nullable = false)
    private Integer peopleAmount;

    @Column(length = 10, nullable = false)
    private String roomType;
    @Column(length = 10, nullable = false)
    private Double total;

    //RELACIONES
    @OneToOne
    private HotelModel hotelModel;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<PeopleModel> people;
    @OneToOne(cascade = CascadeType.PERSIST)
    private PaymentMethodModel paymentMethod;

}
