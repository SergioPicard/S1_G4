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


        String habitacionDisponible = bookedHotel.getTipoHabitacion();
        String habitacionElegida = bookingRequest.getBooking().getRoomType().toUpperCase();
        int nroPersonas = bookingRequest.getBooking().getPeopleAmount();
        boolean destino = bookedHotel.getLugar().equalsIgnoreCase(bookingRequest.getBooking().getDestination());

        /*System.out.println(habitacionElegida);
        System.out.println(habitacionDisponible);
        System.out.println(nroPersonas);
        System.out.println(bookedHotel);
        System.out.println(destino);*/


        if (destino){
        if (habitacionElegida.equalsIgnoreCase(habitacionDisponible)){ //SI EL HOTEL POSEE EL TIPO DE HABITACIÓN SEGUIMOS
        if (nroPersonas != 0 ){  // SI ES MÁS DE UNA PERSONA, SEGUIMOS
        switch (habitacionElegida){ //INICIAMOS SWTICH PARA LOS 4 TIPO DE HABITACIONES EXISTENTES ENTRE TODOS LOS HOTELES
            case "SINGLE":{
                if (nroPersonas > 1){
                    throw new SinHotelesException("No puede ingresar " + nroPersonas + " personas en una habitación tipo Single." );
                }
                break;
            }
            case "DOBLE":{
                if (nroPersonas > 2){
                    throw new SinHotelesException("No puede ingresar " + nroPersonas + " personas en una habitación tipo Doble." );
                }
                break;
            }
            case "TRIPLE":{
                if (nroPersonas > 3){
                    throw new SinHotelesException("No puede ingresar " + nroPersonas + " personas en una habitación tipo Triple." );
                }
                break;
            }
            case "MÚLTIPLE":{
                if (nroPersonas > 4){
                    throw new SinHotelesException("No puede ingresar " + nroPersonas + " personas en una habitación tipo Múltiple." );
                }
                break;
            }
            default: { //UN DEFAULT AL QUE NUNCA VAMOS A LLEGAR
                throw new SinHotelesException("Si sale esto, rompí algo");
            }
        } // TERMINA SWTICH
            
         } else { // EN CASO DE QUE SE COLOQUE 0 EN LAS PERSONAS TOTALES
                throw new SinHotelesException("No puede colocar 0 en las personas que ingresarán a la habitación.");
        }
         } else 
         { // EN CASO DE QUE LA HABITACION SOLICITADA NO ESTE EN EL HOTEL, LE OFRECEMOS LA QUE POSEEMOS EN EL MISMO
            throw new SinHotelesException("No poseemos este tipo de habitación en el hotel seleccionado. Le podemos ofrecer una habitación "
                    + habitacionDisponible + ".");
        }
        } else {
            throw new SinHotelesException("El Hotel '" + bookedHotel.getNombre() + "' se encuentra en " + bookedHotel.getLugar() + ", no en "
                    + bookingRequest.getBooking().getDestination());
        }

                booking.setHotelCode(bookingRequest.getBooking().getHotelCode());
                booking.setPeopleAmount(bookingRequest.getBooking().getPeopleAmount());
                booking.setRoomType(bookingRequest.getBooking().getRoomType().toUpperCase());
                booking.setPeople(bookingRequest.getBooking().getPeople());
                booking.setDateFrom(bookingRequest.getBooking().getDateFrom());
                booking.setDatoTo(bookingRequest.getBooking().getDatoTo());
                booking.setDestination(bookedHotel.getLugar());

       /*if(!bookedHotel.getDisponibleDesde().isAfter(bookingRequest.getBooking().getDateFrom()) &&
            !bookedHotel.getDisponibleHasta().isBefore(bookingRequest.getBooking().getDatoTo())){

            booking.setDateFrom(bookingRequest.getBooking().getDateFrom());
            booking.setDatoTo(bookingRequest.getBooking().getDatoTo());

        /*}

        /*if(bookedHotel.getLugar().equalsIgnoreCase(bookingRequest.getBooking().getDestination())){
            booking.setDestination(bookingRequest.getBooking().getDestination());
        }*/

                //CALCULO DE CANTIDAD DE DIAS DE RESERVA
                Integer bookingDays = Period.between(bookingRequest.getBooking().getDateFrom(),
                        bookingRequest.getBooking().getDatoTo()).getDays();

                //SETEO DEL RESPONSE
                response.setBooking(booking);
                response.setUserName(bookingRequest.getUserName());
                response.setStatus(new StatusCodeDto(200,"Reserva Satisfactoria"));
                response.setTotal(bookedHotel.getPrecioNoche() * bookingDays);


       /* booking.setHotelCode(bookingRequest.getBooking().getHotelCode());
        booking.setPeopleAmount(bookingRequest.getBooking().getPeopleAmount());
        booking.setRoomType(bookingRequest.getBooking().getRoomType());
        booking.setPeople(bookingRequest.getBooking().getPeople());
        booking.setDateFrom(bookingRequest.getBooking().getDateFrom());
        booking.setDatoTo(bookingRequest.getBooking().getDatoTo());
        booking.setDestination(bookingRequest.getBooking().getDestination());

       /*if(!bookedHotel.getDisponibleDesde().isAfter(bookingRequest.getBooking().getDateFrom()) &&
            !bookedHotel.getDisponibleHasta().isBefore(bookingRequest.getBooking().getDatoTo())){

            booking.setDateFrom(bookingRequest.getBooking().getDateFrom());
            booking.setDatoTo(bookingRequest.getBooking().getDatoTo());

        /*}

        /*if(bookedHotel.getLugar().equalsIgnoreCase(bookingRequest.getBooking().getDestination())){
            booking.setDestination(bookingRequest.getBooking().getDestination());
        }*/

        //CALCULO DE CANTIDAD DE DIAS DE RESERVA


        //SETEO DEL RESPONSE

            return response;
    }

}
