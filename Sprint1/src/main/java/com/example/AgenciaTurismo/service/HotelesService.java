package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.request.BookingRequestDto;
import com.example.AgenciaTurismo.dto.response.BookingResDto;
import com.example.AgenciaTurismo.dto.response.BookingResponseDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.dto.response.StatusCodeDto;
import com.example.AgenciaTurismo.models.HotelModel;
import com.example.AgenciaTurismo.repository.HotelesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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

    public BookingResponseDto bookingResponse(BookingRequestDto bookingRequest){
        /*PROBAR EL BOOKINGREQUEST.
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setBooking(bookingRequest.getBooking());
        bookingRequestDto.setUserName(bookingRequest.getUserName());*/

        BookingResponseDto response = new BookingResponseDto();

        //NUEVA RESPONSE RESERVA - DATOS SIN MEDIOS DE PAGO
        BookingResDto booking = new BookingResDto();
        booking.setDateFrom(bookingRequest.getBooking().getDateFrom());
        booking.setDatoTo(bookingRequest.getBooking().getDatoTo());
        booking.setDestination(bookingRequest.getBooking().getDestination());
        booking.setHotelCode(bookingRequest.getBooking().getHotelCode());
        booking.setPeopleAmount(bookingRequest.getBooking().getPeopleAmount());
        booking.setRoomType(bookingRequest.getBooking().getRoomType());
        booking.setPeople(bookingRequest.getBooking().getPeople());

        //BUSQUEDA DEL HOTEL POR CODIGO PASADO EN EL REQUEST.
        HotelModel bookedHotel = hotelesRepository.findHotel(bookingRequest.getBooking().getHotelCode());

        //CALCULO DE CANTIDAD DE DIAS DE RESERVA
        Integer bookingDays = Period.between(bookingRequest.getBooking().getDateFrom(),
                bookingRequest.getBooking().getDatoTo()).getDays();

        //SETEO DEL RESPONSE
        response.setBooking(booking);
        response.setUserName(bookingRequest.getUserName());
        response.setStatus(new StatusCodeDto(200,"Reserva Satisfactoria"));
        response.setTotal(bookedHotel.getPrecioNoche() * bookingDays);

        return response;
    }

}
