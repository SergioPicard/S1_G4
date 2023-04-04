package com.example.AgenciaTurismo.repository;

import com.example.AgenciaTurismo.dto.response.CarsAvailableDTO;
import com.example.AgenciaTurismo.models.CarsModel;
import com.example.AgenciaTurismo.models.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ICarsRepository extends JpaRepository<CarsModel, Integer>  {
    List<CarsModel> findByCodigoAuto(String codigo);
    List<CarsModel> findByDisponibleDesdeLessThanEqualAndDisponibleHastaGreaterThanEqualAndLugar
            (LocalDate disponibleDesde, LocalDate disponibleHasta, String lugar);


}