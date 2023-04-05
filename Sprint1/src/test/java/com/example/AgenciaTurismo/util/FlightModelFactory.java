package com.example.AgenciaTurismo.util;

import com.example.AgenciaTurismo.models.FlightModel;

import java.time.LocalDate;
import java.util.List;

public class FlightModelFactory {
    public static FlightModel getBapi(){
        return FlightModel.builder()
                .id(1)
                .nroVuelo("BAPI-1235")
                .origen("Buenos Aires")
                .destino("Puerto Iguazú")
                .tipoAsiento("Economy")
                .precioPersona(6500.0)
                .fechaIda(LocalDate.of(2022,02,10))
                .fechaVuelta(LocalDate.of(2022,02,15))
                .build();
    }
    public static FlightModel gePibaNoId(){
        return FlightModel.builder()
                .nroVuelo("PIBA-1420")
                .origen("Puerto Iguazú")
                .destino("Bogotá")
                .tipoAsiento("Business")
                .precioPersona(43200.0)
                .fechaIda(LocalDate.of(2022,02,10))
                .fechaVuelta(LocalDate.of(2022,02,20))
                .build();
    }

    public static FlightModel getPiba(){
        return FlightModel.builder()
                .id(2)
                .nroVuelo("PIBA-1420")
                .origen("Puerto Iguazú")
                .destino("Bogotá")
                .tipoAsiento("Business")
                .precioPersona(43200.0)
                .fechaIda(LocalDate.of(2022,02,10))
                .fechaVuelta(LocalDate.of(2022,02,20))
                .build();
    }

    public static List<FlightModel> listFlights(){
        return List.of(
                getBapi(),
                getPiba()
        );
    }
}
