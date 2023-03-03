package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.service.HotelesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class HotelesController {
    @Autowired
    HotelesService hotelesService;

    @GetMapping("/hotels")
    public List<HotelAvailableDto> searchAllHotels(){
        return hotelesService.searchAll();
    }

    @GetMapping("/hotel")
    public List<HotelAvailableDto> filterHotels(@RequestParam("dateFrom") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
                                                @RequestParam("dateTo") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
                                                @RequestParam("destination") String destination ){

        return hotelesService.filterHotels(dateFrom, dateTo, destination);
    }
}
