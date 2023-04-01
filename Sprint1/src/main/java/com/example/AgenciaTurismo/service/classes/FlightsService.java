package com.example.AgenciaTurismo.service.classes;

import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.request.FlightReservationReqDto;
import com.example.AgenciaTurismo.dto.response.*;
import com.example.AgenciaTurismo.exceptions.VuelosException;
import com.example.AgenciaTurismo.models.*;
import com.example.AgenciaTurismo.repository.IFlightsBookingRepository;
import com.example.AgenciaTurismo.repository.IFlightsRepository;
import com.example.AgenciaTurismo.service.generics.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FlightsService implements ICrudService<FlightsAvailableDto,Integer,String> {

    @Autowired
    IFlightsRepository flightsRepository;

    @Autowired
    IFlightsBookingRepository flightsBookingRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public FlightsAvailableDto saveEntity(FlightsAvailableDto flightsAvailableDto) {
        var entity = mapper.map(flightsAvailableDto, FlightModel.class);
        // guardar
        flightsRepository.save(entity);
        // mappear de entity a dto para llevar al controller
        return mapper.map(entity, FlightsAvailableDto.class);
    }

    @Override
    public List<FlightsAvailableDto> getAllEntities() {
        var list = flightsRepository.findAll();
        return list.stream().map(
                        flight -> mapper.map(flight, FlightsAvailableDto.class)
                )
                .collect(Collectors.toList());
    }

    @Override
    public FlightsAvailableDto getEntityById(Integer integer) {
        var entity = flightsRepository.findById(integer).orElseThrow(
                () -> {throw  new VuelosException("No se encontró el vuelo con el id: " + integer);}
        );

        return mapper.map(entity, FlightsAvailableDto.class);
    }


    @Override
    public MessageDTO deleteEntity(String code) {
        // buscar el dato en la base de datos y asegurarnos que exista
        List<FlightModel> exists = flightsRepository.findByNroVuelo(code);
        // eliminar efectivamente
        if(!exists.isEmpty())
            flightsRepository.deleteAll();
        else
            return MessageDTO.builder()
                    .message("No se pudo encontrar el Vuelo a eliminar")
                    .name("ELIMINACION")
                    .build();
        // devolver el mensaje DTO
        return MessageDTO.builder()
                .message("Se elimino el Vuelo con el codigo: " + code)
                .name("ELIMINACION")
                .build();
    }

    public List<FlightsAvailableDto> filterEntity(LocalDate dateFrom, LocalDate dateTo,String origin, String destination) {
        // buscar el dato en la base de datos y asegurarnos que exista
        List<FlightModel> list = flightsRepository.findByFechaIdaAndFechaVueltaAndAndOrigenAndDestino(dateFrom, dateTo,origin,destination);

        if (!list.isEmpty()){
        return list.stream().map(
                        hotel -> mapper.map(hotel, FlightsAvailableDto.class)
                )
                .collect(Collectors.toList());
        }else{
            throw new RuntimeException("No hay vuelos en estas fechas");
        }
    }


    public MessageDTO flightReservationResponse(FlightReservationReqDto flightReservationReqDto){

        FlightResponseModel reservationFligth = new FlightResponseModel();

        //BÚSQUEDA DEL VUELO POR CÓDIGO Y TIPO ASIENTO PARA VALIDAR ATRIBUTOS.
        var bookedFlight = flightsRepository.findByNroVueloAndTipoAsientoEquals(flightReservationReqDto.getFlightReservation().getFlightNumber(),
                flightReservationReqDto.getFlightReservation().getSeatType());

        if(bookedFlight == null){
            throw new VuelosException("El vuelo que desea reservar no existe.");
        }


        //MAPEO RESERVA DE VUELO DE DTO A ENTIDAD.
        FlightReservationResModel booking = mapper.map(flightReservationReqDto.getFlightReservation(), FlightReservationResModel.class);

        //SET ATRIBUTO PARA RELACIÓN
        booking.setFlightModel(bookedFlight);


        //  CANTIDAD DE PERSONAS
        int peopleAmount = flightReservationReqDto.getFlightReservation().getSeats();
        // CANTIDAD DE PERSONAS CON SU DETALLE
        int people = flightReservationReqDto.getFlightReservation().getPeople().size();

        //IGUALDAD DE DESTINO
        boolean destination = flightReservationReqDto.getFlightReservation().getDestination().equalsIgnoreCase(bookedFlight.getDestino());
        //IGUALDAD ORIGEN
        boolean origin = flightReservationReqDto.getFlightReservation().getOrigin().equalsIgnoreCase(bookedFlight.getOrigen());

        // IGUAL A LA FECHA DISPONIBLE
        boolean dateFromEqual = bookedFlight.getFechaIda().isEqual(flightReservationReqDto.getFlightReservation().getDateFrom());
        boolean dateToEqual = bookedFlight.getFechaVuelta().isEqual(flightReservationReqDto.getFlightReservation().getDatoTo());

        // TIPO DE ASIENTO
        String seatTypeAvailable = bookedFlight.getTipoAsiento().toUpperCase();
        String seatTypeSelect = flightReservationReqDto.getFlightReservation().getSeatType().toUpperCase();

        //FECHAS
        LocalDate dateFrom = flightReservationReqDto.getFlightReservation().getDateFrom();
        LocalDate dateTo = flightReservationReqDto.getFlightReservation().getDatoTo();

        if (!flightReservationReqDto.getUserName().isEmpty()) {
            if (!dateFrom.isAfter(dateTo)) {
                if (!dateFrom.isEqual(dateTo)) {
                    if (dateFromEqual && dateToEqual) {
                        if (destination && origin) {
                            if (seatTypeSelect.equalsIgnoreCase(seatTypeAvailable)) {
                                if (peopleAmount != 0) {
                                    if (peopleAmount == people) {

                                        //CALCULO DEL TOTAL SIN INTERESES
                                        double total = bookedFlight.getPrecioPersona() * flightReservationReqDto.getFlightReservation().getSeats();

                                        //VERIFICACIÓN 1 SOLA CUOTA CON DÉBITO
                                        if(flightReservationReqDto.getPaymentMethodDto().getType().equalsIgnoreCase("debitcard")){
                                            if(flightReservationReqDto.getPaymentMethodDto().getDues() > 1){
                                                throw new VuelosException("El método de pago es Débito, solo puede elegir 1 cuota.");
                                            }
                                        }

                                        if (flightReservationReqDto.getPaymentMethodDto().getType().equalsIgnoreCase("creditcard")){
                                            int cuotas = flightReservationReqDto.getPaymentMethodDto().getDues();
                                            if (cuotas <= 3){
                                                total *= 1.05;

                                            } else{
                                                total *= 1.10;
                                            }
                                        }

                                        //SET DEL FLIGTHRESERVATION
                                        reservationFligth.setUserName(flightReservationReqDto.getUserName());
                                        reservationFligth.setTotal(total);
                                        reservationFligth.setFlightReservationResModel(booking);


                                    } else {
                                        throw new VuelosException("La cantidad de pasajeros no coincide con la cantidad de personas ingresada.");
                                    }
                                } else {
                                    throw new VuelosException("Número de personas inválido.");
                                }
                            } else {
                                throw new VuelosException("No poseemos este tipo de asiento en el vuelo seleccionado. Le podemos ofrecer uno estilo "
                                        + seatTypeAvailable + ".");
                            }
                        } else {
                            throw new VuelosException("El vuelo '" + bookedFlight.getNroVuelo() + "' se dirige desde " + bookedFlight.getOrigen() + " hacia " + bookedFlight.getDestino() + ", no desde " + flightReservationReqDto.getFlightReservation().getOrigin() + " hacia "
                                    + flightReservationReqDto.getFlightReservation().getDestination());
                        }
                    } else {
                        throw new VuelosException("El vuelo número '" + bookedFlight.getNroVuelo() + "' se encuentra disponible desde el " + bookedFlight.getFechaIda() + " hasta el "
                                + bookedFlight.getFechaVuelta());
                    }
                } else {
                    throw new VuelosException("La fecha de vuelta debe ser mayor a la de ida.");
                }
            } else {
                throw new VuelosException("La fecha de ida debe ser menor a la de vuelta.");
            }
        } else {
            throw new VuelosException("Debe ingresar un nombre de usuario.");
        }

        flightsBookingRepository.save(reservationFligth);

        return MessageDTO.builder()
                .message("Reserva de vuelo dada de alta correctamente." )
                .name("CREACIÓN")
                .build();
    }

    public MessageDTO deleteFlightReservation(Integer id){
        if(flightsBookingRepository.existsById(id)){
            flightsBookingRepository.deleteById(id);

            return MessageDTO.builder()
                    .message("Reserva de vuelo dada de baja correctamente." )
                    .name("ELIMINACIÓN")
                    .build();

        }else{
            return MessageDTO.builder()
                    .message("Reserva de vuelo con id: " + id + " no ha sido encontrada." )
                    .name("ELIMINACIÓN")
                    .build();
        }
    }

/*    @Autowired
    IFlightsRepository flightsRepository;

    public List<FlightsAvailableDto> searchAll(){

        return flightsRepository.findAll();
    }

    public List<FlightsAvailableDto> filterFlights(LocalDate fechaIda, LocalDate fechaVuelta, String origen, String destino){

        List<FlightsAvailableDto> allFlights = flightsRepository.findAll();

        List<FlightsAvailableDto> destinationStatus = allFlights.stream().filter(flight -> Objects.equals(flight.getDestino(), destino)).collect(Collectors.toList());
        List<FlightsAvailableDto> originStatus = allFlights.stream().filter(flight -> Objects.equals(flight.getOrigen(), origen)).collect(Collectors.toList());

        // VALIDACION POR DESTINO
        if (destinationStatus.isEmpty()){
            throw new VuelosException("El destino elegido no existe.");
        }

        // VALIDACION POR ORIGEN
        if (originStatus.isEmpty()){
            throw new VuelosException("El origen elegido no existe.");
        }

        //VALIDACION FECHA ENTRADA MENOR A SALIDA
        if(fechaIda.isAfter(fechaVuelta)){
            throw new VuelosException("La fecha de ida debe ser menor a la de vuelta.");
        }

        //VALIDACION FECHA SALIDA MAYOR A ENTRADA
        if(fechaIda.isEqual(fechaVuelta)){
            throw new VuelosException("La fecha de vuelta debe ser mayor a la de ida");
        }


        List<FlightsAvailableDto> flightsAvailable = flightsRepository.filterFlightRep(fechaIda, fechaVuelta, origen, destino);

        if(flightsAvailable.isEmpty()){
            throw new VuelosException("No se encontraron vuelos disponibles en este periodo de tiempo y en el destino indicado.");
        }


        return flightsAvailable;
    }

    public FlightResponseDto flightReservationResponse(FlightReservationReqDto flightReservationReqDto){

        FlightResponseDto response = new FlightResponseDto();

        //BUSQUEDA DEL VUELO POR CODIGO PASADO EN EL REQUEST.
        FlightsAvailableDto bookedFlight = flightsRepository.findFlight(flightReservationReqDto.getFlightReservation().getFlightNumber(),
                                                                        flightReservationReqDto.getFlightReservation().getSeatType());

        //NUEVA RESPONSE RESERVA - DATOS SIN MEDIOS DE PAGO
        FlightReservationResDto booking = new FlightReservationResDto();

        //  CANTIDAD DE PERSONAS
        int peopleAmount = flightReservationReqDto.getFlightReservation().getSeats();
        // CANTIDAD DE PERSONAS CON SU DETALLE
        int people = flightReservationReqDto.getFlightReservation().getPeople().size();

        //IGUALDAD DE DESTINO
        boolean destination = flightReservationReqDto.getFlightReservation().getDestination().equalsIgnoreCase(bookedFlight.getDestino());
        //IGUALDAD ORIGEN
        boolean origin = flightReservationReqDto.getFlightReservation().getOrigin().equalsIgnoreCase(bookedFlight.getOrigen());

        // IGUAL A LA FECHA DISPONIBLE
        boolean dateFromEqual = bookedFlight.getFechaIda().isEqual(flightReservationReqDto.getFlightReservation().getDateFrom());
        boolean dateToEqual = bookedFlight.getFechaVuelta().isEqual(flightReservationReqDto.getFlightReservation().getDatoTo());

        // TIPO DE ASIENTO
        String seatTypeAvailable = bookedFlight.getTipoAsiento().toUpperCase();
        String seatTypeSelect = flightReservationReqDto.getFlightReservation().getSeatType().toUpperCase();

        //FECHAS
        LocalDate dateFrom = flightReservationReqDto.getFlightReservation().getDateFrom();
        LocalDate dateTo = flightReservationReqDto.getFlightReservation().getDatoTo();

        if (!flightReservationReqDto.getUserName().isEmpty()) {
            if (!dateFrom.isAfter(dateTo)) {
                if (!dateFrom.isEqual(dateTo)) {
                    if (dateFromEqual && dateToEqual) {
                        if (destination && origin) {
                            if (seatTypeSelect.equalsIgnoreCase(seatTypeAvailable)) {
                                if (peopleAmount != 0) {
                                    if (peopleAmount == people) {

                                        booking.setFlightNumber(flightReservationReqDto.getFlightReservation().getFlightNumber());
                                        booking.setOrigin(flightReservationReqDto.getFlightReservation().getOrigin());
                                        booking.setSeats(flightReservationReqDto.getFlightReservation().getSeats());
                                        booking.setPeople(flightReservationReqDto.getFlightReservation().getPeople());
                                        booking.setDateFrom(flightReservationReqDto.getFlightReservation().getDateFrom());
                                        booking.setDatoTo(flightReservationReqDto.getFlightReservation().getDatoTo());
                                        booking.setDestination(bookedFlight.getDestino());
                                        booking.setSeatType(flightReservationReqDto.getFlightReservation().getSeatType());

                                    } else {
                                        throw new VuelosException("La cantidad de pasajeros no coincide con la cantidad de personas ingresada.");
                                    }
                                } else {
                                    throw new VuelosException("Número de personas inválido.");
                                }
                            } else {
                                throw new VuelosException("No poseemos este tipo de asiento en el vuelo seleccionado. Le podemos ofrecer uno estilo "
                                        + seatTypeAvailable + ".");
                            }
                        } else {
                            throw new VuelosException("El vuelo '" + bookedFlight.getNroVuelo() + "' se dirige desde " + bookedFlight.getOrigen() + " hacia " + bookedFlight.getDestino() + ", no desde " + flightReservationReqDto.getFlightReservation().getOrigin() + " hacia "
                                    + flightReservationReqDto.getFlightReservation().getDestination());
                        }
                    } else {
                        throw new VuelosException("El vuelo número '" + bookedFlight.getNroVuelo() + "' se encuentra dispobile desde el " + bookedFlight.getFechaIda() + " hasta el "
                                + bookedFlight.getFechaVuelta());
                    }
                } else {
                    throw new VuelosException("La fecha de vuelta debe ser mayor a la de ida.");
                }
            } else {
                throw new VuelosException("La fecha de ida debe ser menor a la de vuelta.");
            }
        } else {
            throw new VuelosException("Debe ingresar un nombre de usuario.");
        }


        //CALCULO DEL TOTAL SIN INTERESES
        Double total = bookedFlight.getPrecioPersona() * flightReservationReqDto.getFlightReservation().getSeats();

        //VERIFICACION 1 SOLA CUOTA CON DEBITO
        if(flightReservationReqDto.getPaymentMethodDto().getType().equalsIgnoreCase("debitcard")){
            if(flightReservationReqDto.getPaymentMethodDto().getDues() > 1){
                throw new VuelosException("El método de pago es Débito, solo puede elegir 1 cuota.");
            }
        }

        String mensaje = "Reserva Satisfactoria";

        if (flightReservationReqDto.getPaymentMethodDto().getType().equalsIgnoreCase("creditcard")){
            int cuotas = flightReservationReqDto.getPaymentMethodDto().getDues();
            if (cuotas <= 3){
                mensaje = "Reserva Satisfactoria. Por utilizar TC tiene un recargo del 5%. Su recargo es de: $" + total * 0.05;

                total *= 1.05;

            } else{
                mensaje = "Reserva Satisfactoria. Por utilizar TC tiene un recargo del 10%. Su recargo es de: $" + total * 0.10;

                total *= 1.10;

            }
        }

        //SETEO DEL RESPONSE
        response.setFlightReservation(booking);
        response.setUserName(flightReservationReqDto.getUserName());
        response.setStatus(new StatusCodeDto(200,mensaje));
        response.setTotal(total);


        return response;
    }*/

}
