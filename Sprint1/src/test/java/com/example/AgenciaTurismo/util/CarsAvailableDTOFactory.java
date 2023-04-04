package com.example.AgenciaTurismo.util;

import com.example.AgenciaTurismo.dto.response.CarsAvailableDTO;
import com.example.AgenciaTurismo.models.CarsModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarsAvailableDTOFactory {
    public static CarsAvailableDTO getRenault(){
        return CarsAvailableDTO.builder()
                .codigoAuto("CA-0001")
                .modelo("Renault-Logan")
                .lugar("Puerto Iguazú")
                .precioPorDia(6300.0)
                .disponibleDesde(LocalDate.of(2022,02,10))
                .disponibleHasta(LocalDate.of(2022,03,20))
                .reservado(false)
                .build();
    }
    public static CarsAvailableDTO getToyota(){
        return CarsAvailableDTO.builder()
                .codigoAuto("CA-0002")
                .modelo("Toyota-Etios Hatch")
                .lugar("Puerto Iguazú")
                .precioPorDia(6500.0)
                .disponibleDesde(LocalDate.of(2022,02,10))
                .disponibleHasta(LocalDate.of(2022,03,23))
                .reservado(false)
                .build();
    }
    public static CarsAvailableDTO getNissan(){
        return CarsAvailableDTO.builder()
                .codigoAuto("CA-0003")
                .modelo("Nissan-Versa")
                .lugar("Buenos Aires")
                .precioPorDia(12000.0)
                .disponibleDesde(LocalDate.of(2022,02,10))
                .disponibleHasta(LocalDate.of(2022,03,19))
                .reservado(false)
                .build();
    }
    public static List<CarsAvailableDTO> listCars(){
        List<CarsAvailableDTO> list = new ArrayList<>();
        list.add((getRenault()));
        list.add(getToyota());
        list.add((getNissan()));

        return list;
    }
}
