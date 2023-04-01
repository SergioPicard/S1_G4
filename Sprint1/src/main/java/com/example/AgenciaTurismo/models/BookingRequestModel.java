package com.example.AgenciaTurismo.models;

import com.example.AgenciaTurismo.dto.request.BookingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "reservaHotel")

public class BookingRequestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 40, nullable = false)
    private String userName;

    //RELACIONES
    @OneToOne
    private BookingModel booking;

}
