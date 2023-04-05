
package com.example.AgenciaTurismo.unit.service;

import com.example.AgenciaTurismo.dto.response.*;
import com.example.AgenciaTurismo.exceptions.CustomException;
import com.example.AgenciaTurismo.repository.IBookingModelRepository;
import com.example.AgenciaTurismo.repository.IHotelesRepository;
import com.example.AgenciaTurismo.service.classes.HotelesService;
import com.example.AgenciaTurismo.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class HotelesServiceTest {

    @Mock
    IHotelesRepository hotelesRepository;

    @Mock
    IBookingModelRepository bookingModelRepository;

    @InjectMocks
    HotelesService hotelesService;

    @Test
    @DisplayName("Se buscan todos los hoteles - SERVICE")
    void searchAllTest() {
        // arrange
        List<HotelAvailableDto> expected = HotelAvailableDtoFactory.listHotels();

        // act
        Mockito.when(hotelesRepository.findAll()).thenReturn(HotelModelFactory.listHotels());
        var result = hotelesService.getAllEntities();

        // assert
        Assertions.assertEquals(expected,result);
    }

    @Test
    @DisplayName("Se buscan todas los Hoteles de un tipo de habitacion")
    void findRoomTypeTest(){
        // arrange
        String param = "doble";
        var expected = List.of(HotelAvailableDtoFactory.cataratasHotel());
        var model = HotelModelFactory.cataratasHotel();
        model.setId(1);

        // act
        Mockito.when(hotelesRepository.findByTipoHabitacionEquals(param)).thenReturn(List.of(model));

        var result = hotelesService.findRoomType(param);

        // assert
        Assertions.assertEquals(expected, result);
    }
    @Test
    @DisplayName("Se buscan todas los Hoteles de un tipo de habitacion")
    void findRoomTypeExeptionTest(){
        // arrange
        String param = "ee";

        // act
        Mockito.when(hotelesRepository.findByTipoHabitacionEquals(param)).thenReturn(List.of());

//        var result = hotelesService.findRoomType(param);

        // assert
        Assertions.assertThrows(CustomException.class, ()-> hotelesService
                .findRoomType(param));
    }
    @Test
    @DisplayName("Excepcion por no encontrar un hotel con este precio.")
    void findByPrecioNocheLessThanEqualExceptionTest(){
        // arrange
        Double param = (double) 100;

        // act
        Mockito.when(hotelesRepository.findByPrecioNocheLessThanEqual(param)).thenReturn(List.of());

//        var result = hotelesService.findRoomType(param);

        // assert
        Assertions.assertThrows(CustomException.class, ()-> hotelesService
                .findByPrecioNocheLessThanEqual(param));
    }
    @Test
    @DisplayName("Se buscan todas los Hoteles con un precio menor o igual al definido")
    void findByPrecioNocheLessThanEqualTest(){
        // arrange
        Double param = (double) 6500;
        var expected = List.of(HotelAvailableDtoFactory.cataratasHotel(), HotelAvailableDtoFactory.BristolHotel());

        // act
        Mockito.when(hotelesRepository.findByPrecioNocheLessThanEqual(param)).thenReturn(List.of(HotelModelFactory.cataratasHotel(), HotelModelFactory.BristolHotel()));

        var result = hotelesService.findByPrecioNocheLessThanEqual(param);

        // assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Se buscan todos los id de hoteles con reserva y cantidad de personas")
    void getIdHotelPeopleAmountExceptionTest(){
        // arrange

        // act
        Mockito.when(bookingModelRepository.getIdHotelPeopleAmount()).thenReturn(List.of());

        // assert
        Assertions.assertThrows(CustomException.class, ()-> hotelesService
                .getIdHotelPeopleAmount());
    }

}
