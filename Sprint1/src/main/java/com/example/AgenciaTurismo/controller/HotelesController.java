package com.example.AgenciaTurismo.controller;

import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.request.BookingRequestDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;

import com.example.AgenciaTurismo.models.HotelModel;
import com.example.AgenciaTurismo.service.classes.HotelesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/")
public class HotelesController {
    @Autowired
    HotelesService hotelesService;

   @GetMapping("/hotels")
    public ResponseEntity<List<HotelAvailableDto>> findAllHotels(){
        return ResponseEntity.ok(hotelesService.getAllEntities());
    }

    @PostMapping("/hotels/new")
    public ResponseEntity<HotelAvailableDto> newHotel(@RequestBody HotelAvailableDto hotelAvailableDto){
        return ResponseEntity.ok(
                hotelesService.saveEntity(hotelAvailableDto)
        );
    }
    @DeleteMapping("/hotels/{code}")
    public ResponseEntity<MessageDTO> deleteByIdHotel(@PathVariable String code){
        return ResponseEntity.ok(
                hotelesService.deleteEntity(code)
        );
    }

    @GetMapping("/hotel")
    public List<HotelAvailableDto> filterHotels(@RequestParam("dateFrom") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
                                                @RequestParam("dateTo") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
                                                @RequestParam("destination") String destination ){

        return hotelesService.filterEntity(dateFrom, dateTo, destination);
    }

    @PostMapping("/hotel-booking/new")
    public ResponseEntity<MessageDTO> booking(@RequestBody @Valid BookingRequestDto bookingRequest){
        return ResponseEntity.ok(
            hotelesService.bookingResponse(bookingRequest));
    }

    @DeleteMapping("/hotel-booking/delete/{id}")
    public  ResponseEntity<MessageDTO> deleteHotelReservation(@PathVariable Integer id){
       return  ResponseEntity.ok(
               hotelesService.deleteHotelReservation(id));
    }

    @PutMapping("/hotels/edit")
    public  ResponseEntity<MessageDTO> editHotels(@RequestParam String code,
                                 @RequestBody HotelModel hotelModel){

        return  ResponseEntity.ok(
                hotelesService.editEntity(code, hotelModel));
    }

   }


 /*

    @PostMapping("/booking")
    public BookingResponseDto booking(@RequestBody @Valid BookingRequestDto bookingRequest){

        return hotelesService.bookingResponse(bookingRequest);
    }*/

