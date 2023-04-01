package com.example.AgenciaTurismo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "estado")

public class StatusCodeModel {

    @Id
    @Column(length = 3, nullable = false)
    private Integer code;

    @Column(length = 50, nullable = false)
    private String message;

}
