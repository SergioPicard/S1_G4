package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.request.BookingRequestDto;
import com.example.AgenciaTurismo.dto.response.BookingResDto;
import com.example.AgenciaTurismo.dto.response.BookingResponseDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.dto.response.StatusCodeDto;
import com.example.AgenciaTurismo.exceptions.SinHotelesException;
import com.example.AgenciaTurismo.models.HotelModel;
import com.example.AgenciaTurismo.repository.HotelesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

        BookingRequestDto habitacionCapacidad = new BookingRequestDto();

        if(hotelAvailable.isEmpty()){
            throw new SinHotelesException("No se encontraron hoteles disponibles en esta fecha.");
        }


       /* for (HotelAvailableDto habitacion: hotelAvailable) {
            if (habitacion.getTipoHabitacion().equalsIgnoreCase("Double")) {
                if (habitacionCapacidad.getBooking().getPeopleAmount())
            }
        }*/

        return hotelAvailable;
    }

    public BookingResponseDto bookingResponse(BookingRequestDto bookingRequest){

        BookingResponseDto response = new BookingResponseDto();

        //NUEVA RESPONSE RESERVA - DATOS SIN MEDIOS DE PAGO
        BookingResDto booking = new BookingResDto();

        //BUSQUEDA DEL HOTEL POR CODIGO PASADO EN EL REQUEST.
        HotelModel bookedHotel = hotelesRepository.findHotel(bookingRequest.getBooking().getHotelCode());


        String roomAvailable = bookedHotel.getTipoHabitacion();
        String roomSelect = bookingRequest.getBooking().getRoomType().toUpperCase();

        int peopleAmount = bookingRequest.getBooking().getPeopleAmount();

        boolean destiny = bookedHotel.getLugar().equalsIgnoreCase(bookingRequest.getBooking().getDestination());

        boolean dateFrom = bookedHotel.getDisponibleDesde().isAfter(bookingRequest.getBooking().getDateFrom());
        boolean dateTo = bookedHotel.getDisponibleHasta().isBefore(bookingRequest.getBooking().getDatoTo());

        boolean dateEqualFrom = bookedHotel.getDisponibleDesde().isEqual(bookingRequest.getBooking().getDateFrom());
        boolean dateEqualTo = bookedHotel.getDisponibleHasta().isEqual(bookingRequest.getBooking().getDatoTo());

        
        //VERIFICAMOS DISPONIBILIDAD EN ESAS FECHAS
        if (!dateFrom && !dateTo || dateEqualFrom && dateEqualTo){
            //VERIFICAMOS DE QUE EL DESTINO SOLICITADO ESTÉ EN EL MISMO LUGAR QUE EL HOTEL
        if (destiny){
            //SI EL HOTEL POSEE EL TIPO DE HABITACIÓN SEGUIMOS
        if (roomSelect.equalsIgnoreCase(roomAvailable)){
        if (peopleAmount != 0 ){
            // SI ES MÁS DE UNA PERSONA, SEGUIMOS
        switch (roomSelect){
            //INICIAMOS SWTICH PARA LOS 4 TIPO DE HABITACIONES EXISTENTES ENTRE TODOS LOS HOTELES
            case "SINGLE":{
                if (peopleAmount > 1){
                    throw new SinHotelesException("No puede ingresar " + peopleAmount + " personas en una habitación tipo Single." );
                }
                break;
            }
            case "DOBLE":{
                if (peopleAmount > 2){
                    throw new SinHotelesException("No puede ingresar " + peopleAmount + " personas en una habitación tipo Doble." );
                }
                break;
            }
            case "TRIPLE":{
                if (peopleAmount > 3){
                    throw new SinHotelesException("No puede ingresar " + peopleAmount + " personas en una habitación tipo Triple." );
                }
                break;
            }
            case "MÚLTIPLE":{
                if (peopleAmount > 4){
                    throw new SinHotelesException("No puede ingresar " + peopleAmount + " personas en una habitación tipo Múltiple." );
                }
                break;
            }
            //UN DEFAULT AL QUE NUNCA VAMOS A LLEGAR
            default: {
                throw new SinHotelesException("Si sale esto, rompí algo");
            }
        }
        // EN CASO DE QUE SE COLOQUE 0 EN LAS PERSONAS TOTALES
         } else {
                throw new SinHotelesException("No puede colocar 0 en las personas que ingresarán a la habitación.");
        }
        // EN CASO DE QUE LA HABITACION SOLICITADA NO ESTE EN EL HOTEL, LE OFRECEMOS LA QUE POSEEMOS EN EL MISMO
         } else 
         {
            throw new SinHotelesException("No poseemos este tipo de habitación en el hotel seleccionado. Le podemos ofrecer una habitación "
                    + roomAvailable + ".");
        }
        // EN CASO DE QUE EL DESTINO SOLICITADO SEA DISTINTO AL DEL HOTEL BUSCADO
        } else {
            throw new SinHotelesException("El Hotel '" + bookedHotel.getNombre() + "' se encuentra en " + bookedHotel.getLugar() + ", no en "
                    + bookingRequest.getBooking().getDestination());
        }
        } else {
            throw new SinHotelesException("El Hotel '" + bookedHotel.getNombre() + "' se encuentra dispobile desde el " + bookedHotel.getDisponibleDesde() + " hasta el "
                    + bookedHotel.getDisponibleHasta());
        }

                booking.setHotelCode(bookingRequest.getBooking().getHotelCode());
                booking.setPeopleAmount(bookingRequest.getBooking().getPeopleAmount());
                booking.setRoomType(bookingRequest.getBooking().getRoomType().toUpperCase());
                booking.setPeople(bookingRequest.getBooking().getPeople());
                booking.setDateFrom(bookingRequest.getBooking().getDateFrom());
                booking.setDatoTo(bookingRequest.getBooking().getDatoTo());
                booking.setDestination(bookedHotel.getLugar());


                //CALCULO DE CANTIDAD DE DIAS DE DIF
                int bookingDays = bookingRequest.getBooking().getDatoTo().getDayOfYear() - bookingRequest.getBooking().getDateFrom().getDayOfYear();
                /*Integer bookingDays = Period.between(bookingRequest.getBooking().getDateFrom(),
                        bookingRequest.getBooking().getDatoTo()).getDays();*/

                System.out.println(bookingDays);

                //SETEO DEL RESPONSE
                response.setBooking(booking);
                response.setUserName(bookingRequest.getUserName());
                response.setStatus(new StatusCodeDto(200,"Reserva Satisfactoria"));
                response.setTotal(bookedHotel.getPrecioNoche() * bookingDays);


            return response;
    }

}
