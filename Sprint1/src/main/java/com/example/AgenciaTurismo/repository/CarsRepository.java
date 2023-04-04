
package com.example.AgenciaTurismo.repository;


import com.example.AgenciaTurismo.dto.response.CarsAvailableDTO;
import com.example.AgenciaTurismo.models.CarsModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CarsRepository {
    List<CarsAvailableDTO> carsAvailable = new ArrayList<>();

    public CarsRepository() {
        dataBase();
    }

    public List<CarsModel> dataBase() {
        List<CarsModel> autos = null;
        File file;
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule());
        TypeReference<List<CarsModel>> typeRef = new TypeReference<>() {
        };

        try {
            file = ResourceUtils.getFile("classpath:JSON/cars.json");
            autos = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert autos != null;
        for (CarsModel auto : autos) {
            carsAvailable.add(new CarsAvailableDTO(auto.getCodigoAuto(),auto.getModelo(),auto.getLugar(),auto.getPrecioPorDia(),
                    auto.getDisponibleDesde(),auto.getDisponibleHasta(),auto.getReservado()));
        }
        return autos;
    }
    public List<CarsAvailableDTO> findAll(){
        return carsAvailable;
    }

}

