package com.example.AgenciaTurismo.service.classes;

import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.response.CarsAvailableDTO;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.exceptions.CarsException;
import com.example.AgenciaTurismo.models.CarsModel;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.repository.*;
import com.example.AgenciaTurismo.service.generics.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarsService implements ICrudService <CarsAvailableDTO,Integer,String> {
    @Autowired
    ICarsRepository carsRepository;

    @Autowired
    ICarsBookingRepository carsBookingRepository;
    @Autowired
    ICarsBookingRequestRepository carsReservationResRepository;

    ModelMapper mapper = new ModelMapper();


    @Override
    public MessageDTO saveEntity(CarsAvailableDTO carsDTO) {
        var entity = mapper.map(carsDTO, CarsModel.class);
        // guardar
        carsRepository.save(entity);
        // mappear de entity a dto para llevar al controller

        return MessageDTO.builder()
                .message("Auto dado de alta correctamente." )
                .name("CREACIÓN")
                .build();
    }

    @Override
    //listar
    public List<CarsAvailableDTO> getAllEntities() {
        //buscar todos los resultados en el repo
        var list = carsRepository.findAll();

        //convertir de model a DTO
        return list.stream().map(
                        carsModel -> mapper.map(carsModel, CarsAvailableDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CarsAvailableDTO getEntityById(Integer Id) {
        //llamo al repo y le paso el ID
        //Si esta vacio devuelvo excepcion
        //mapeo de entidad a DTO
        var entity = carsRepository.findById(Id).orElseThrow(
                () -> {
                    throw new CarsException("No se encontró el auto con el id: " + Id);
                }
        );
        return mapper.map(entity, CarsAvailableDTO.class);
    }

    @Override
    public MessageDTO deleteEntity(String code) {
        // buscar el dato en la base de datos y asegurarnos que exista
        List<CarsModel> exists = carsRepository.findByCodigoAuto(code);
        // eliminar efectivamente
        if (!exists.isEmpty())
            carsRepository.deleteAll();
        else
            return MessageDTO.builder()
                    .message("No se pudo encontrar el auto a eliminar")
                    .name("ELIMINACION")
                    .build();
        // devolver el mensaje DTO
        return MessageDTO.builder()
                .message("Se elimino el auto con el codigo: " + code)
                .name("ELIMINACION")
                .build();
    }
    public List<CarsAvailableDTO> filterEntity(LocalDate disponibleDesde, LocalDate disponibleHasta, String lugar) {
        // buscar el dato en la base de datos y asegurarnos que exista
        List<CarsModel> list = carsRepository.findByDisponibleDesdeLessThanEqualAndDisponibleHastaGreaterThanEqualAndLugar(disponibleDesde, disponibleHasta, lugar);

        if (!list.isEmpty()){
            return list.stream().map(
                            hotel -> mapper.map(hotel, CarsAvailableDTO.class)
                    )
                    .collect(Collectors.toList());
        } else{
            throw new RuntimeException("No hay autos en este lugar, en estas fechas");
        }
    }
}
