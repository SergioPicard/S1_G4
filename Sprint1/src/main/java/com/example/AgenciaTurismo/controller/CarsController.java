package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.response.CarsAvailableDTO;
import com.example.AgenciaTurismo.service.classes.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/")
public class CarsController {
    @Autowired
    CarsService carsService;

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> newCars(@RequestBody CarsAvailableDTO carsAvailableDTO) {
        return ResponseEntity.ok(
                carsService.saveEntity(carsAvailableDTO)
        );
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarsAvailableDTO>> findAllCars() {
        return ResponseEntity.ok(carsService.getAllEntities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarsAvailableDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(carsService.getEntityById(id));
    }

    @DeleteMapping("/cars/{code}")
    public ResponseEntity<MessageDTO> deleteByIdCars(@PathVariable String code) {
        return ResponseEntity.ok(
                carsService.deleteEntity(code)
        );
    }
}