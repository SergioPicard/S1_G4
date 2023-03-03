package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.repository.HotelesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HotelesService {
    @Autowired
    HotelesRepository hotelesRepository;

    public List<HotelAvailableDto> searchAll(){
        return hotelesRepository.findAll();
    }

    public List<HotelAvailableDto> filterHotels(LocalDate dateFrom, LocalDate dateTo, String destination){

        List<HotelAvailableDto> hotelAvailable = hotelesRepository.filterHotelsRep(dateFrom, dateTo, destination);

        if(hotelAvailable.isEmpty()){
            throw new RuntimeException("No se encontraron hoteles disponibles en este periodo de tiempo y en el destino indicado :(.");
        }

        return hotelAvailable;
    }

}
