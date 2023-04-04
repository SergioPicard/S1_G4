package com.example.AgenciaTurismo.unit.repository;

import com.example.AgenciaTurismo.dto.response.CarsAvailableDTO;
import com.example.AgenciaTurismo.repository.CarsRepository;
import com.example.AgenciaTurismo.util.CarsAvailableDTOFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CarsRepositoryTest {
    CarsRepository carsRepository = new CarsRepository();

    @Test
    @DisplayName("se buscan todos los autos - REPOSITORY")
    void findAll(){
        // arrange
        List<CarsAvailableDTO> expected = List.of(CarsAvailableDTOFactory.getRenault(),CarsAvailableDTOFactory.getToyota(),CarsAvailableDTOFactory.getNissan());
        // act
        var result = carsRepository.findAll();
        // assert
        Assertions.assertEquals(expected, result);
    }
}
