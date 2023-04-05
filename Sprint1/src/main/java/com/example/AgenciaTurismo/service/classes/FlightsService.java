package com.example.AgenciaTurismo.service.classes;

import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.request.BookingDto;
import com.example.AgenciaTurismo.dto.request.FlightReservationReqDto;
import com.example.AgenciaTurismo.dto.response.*;
import com.example.AgenciaTurismo.exceptions.CustomException;
import com.example.AgenciaTurismo.exceptions.VuelosException;
import com.example.AgenciaTurismo.models.*;
import com.example.AgenciaTurismo.repository.IFlightReservationResRepository;
import com.example.AgenciaTurismo.repository.IFlightsBookingRepository;
import com.example.AgenciaTurismo.repository.IFlightsRepository;
import com.example.AgenciaTurismo.service.generics.ICrudService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FlightsService implements ICrudService<FlightsAvailableDto,Integer,String> {

    @Autowired
    IFlightsRepository flightsRepository;

    @Autowired
    IFlightsBookingRepository flightsBookingRepository;

    @Autowired
    IFlightReservationResRepository flightReservationResRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public MessageDTO saveEntity(FlightsAvailableDto flightsAvailableDto) {
        var entity = mapper.map(flightsAvailableDto, FlightModel.class);
        // guardar
        flightsRepository.save(entity);
        // mappear de entity a dto para llevar al controller

        return MessageDTO.builder()
                .message("Vuelo dado de alta correctamente.")
                .name("CREACIÓN")
                .build();
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
                () -> {
                    throw new VuelosException("No se encontró el vuelo con el id: " + integer);
                }
        );

        return mapper.map(entity, FlightsAvailableDto.class);
    }

    @Override
    public MessageDTO deleteEntity(String code) {
        // buscar el dato en la base de datos y asegurarnos que exista
        List<FlightModel> exists = flightsRepository.findByNroVuelo(code);
        // eliminar efectivamente
        if (!exists.isEmpty())
            if (!flightReservationResRepository.findByFlightNumber(code).isEmpty()) {
                throw new CustomException("ELIMINACIÓN", "Existe una reserva con dicho vuelo. Cancelar la reserva antes de eliminar el vuelo.");
            } else {
                for (FlightModel flight : exists) {
                    flightsRepository.delete(flight);
                }
            }
        else
            throw new CustomException("ELIMINACIÓN", "No se pudo encontrar el vuelo con código: " + code);

        // devolver el mensaje DTO
        return MessageDTO.builder()
                .message("Se elimino el Vuelo con el código: " + code)
                .name("ELIMINACIÓN")
                .build();
    }

    @SneakyThrows
    public List<FlightsAvailableDto> filterEntity(LocalDate dateFrom, LocalDate dateTo, String origin, String destination) {
        // buscar el dato en la base de datos y asegurarnos que exista
        List<FlightModel> list = flightsRepository.findByFechaIdaAndFechaVueltaAndAndOrigenAndDestino(dateFrom, dateTo, origin, destination);

        System.out.println(dateTo);
        if (origin == null && destination == null && destination.equals(" ") && origin.equals(" ")) {
            throw new CustomException("FILTRAR", "Debe ingresar un destino y un origen");
        }

        if (dateFrom.isAfter(dateTo) || dateFrom.equals(dateTo)) {
            throw new CustomException("FILTRAR", "La fecha de ida debe ser menor a la de salida.");
        }

        if (list.isEmpty()) {
            throw new CustomException("FILTRAR", "No se encontraron vuelos en estas fechas y destino.");
        }

        return list.stream().map(
                        hotel -> mapper.map(hotel, FlightsAvailableDto.class)
                )
                .collect(Collectors.toList());
    }


    public MessageDTO flightReservationResponse(FlightReservationReqDto flightReservationReqDto) {

        FlightResponseModel reservationFligth = new FlightResponseModel();

        //BÚSQUEDA DE VUELO POR CÓDIGO
        List<FlightModel> vuelos = flightsRepository.findByNroVuelo(flightReservationReqDto.getFlightReservation().getFlightNumber());
        if (vuelos.isEmpty()) {
            throw new VuelosException("El vuelo que desea reservar no existe.");
        }

        //BÚSQUEDA DEL VUELO POR CÓDIGO Y TIPO ASIENTO PARA VALIDAR ATRIBUTOS.
        var bookedFlight = flightsRepository.findByNroVueloAndTipoAsientoEquals(flightReservationReqDto.getFlightReservation().getFlightNumber(),
                flightReservationReqDto.getFlightReservation().getSeatType());

        if (bookedFlight == null) {
            List<String> asientos = new ArrayList<>();
            for (FlightModel vuelo : vuelos) {
                asientos.add(vuelo.getTipoAsiento());
            }
            throw new VuelosException("No poseemos este tipo de asiento en el vuelo seleccionado. Le podemos ofrecer uno estilo "
                    + asientos.get(0) + ".");
        }

        //VALIDACIONES
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

        //FECHAS
        LocalDate dateFrom = flightReservationReqDto.getFlightReservation().getDateFrom();
        LocalDate dateTo = flightReservationReqDto.getFlightReservation().getDatoTo();

        if (!flightReservationReqDto.getUserName().isEmpty()) {
            if (!dateFrom.isAfter(dateTo)) {
                if (!dateFrom.isEqual(dateTo)) {
                    if (dateFromEqual && dateToEqual) {
                        if (destination && origin) {
                            if (peopleAmount != 0) {
                                if (peopleAmount == people) {

                                    //CALCULO DEL TOTAL SIN INTERESES
                                    double total = bookedFlight.getPrecioPersona() * flightReservationReqDto.getFlightReservation().getSeats();

                                    //VERIFICACIÓN 1 SOLA CUOTA CON DÉBITO
                                    if (flightReservationReqDto.getPaymentMethodDto().getType().equalsIgnoreCase("debitcard")) {
                                        if (flightReservationReqDto.getPaymentMethodDto().getDues() > 1) {
                                            throw new VuelosException("El método de pago es Débito, solo puede elegir 1 cuota.");
                                        }
                                    }

                                    if (flightReservationReqDto.getPaymentMethodDto().getType().equalsIgnoreCase("creditcard")) {
                                        int cuotas = flightReservationReqDto.getPaymentMethodDto().getDues();
                                        if (cuotas <= 3) {
                                            total *= 1.05;

                                        } else {
                                            total *= 1.10;
                                        }
                                    }
                                    //MAPEO RESERVA DE VUELO DE DTO A ENTIDAD.
                                    FlightReservationResModel booking = mapper.map(flightReservationReqDto.getFlightReservation(), FlightReservationResModel.class);
                                    PaymentMethodModel paymentMethod = mapper.map(flightReservationReqDto.getPaymentMethodDto(), PaymentMethodModel.class);
                                    booking.setFlightModel(bookedFlight);

                                    //SET DEL FLIGTHRESERVATION
                                    reservationFligth.setUserName(flightReservationReqDto.getUserName());
                                    reservationFligth.setTotal(total);
                                    reservationFligth.setFlightReservationResModel(booking);
                                    reservationFligth.setPaymentMethod(paymentMethod);


                                } else {
                                    throw new VuelosException("La cantidad de pasajeros no coincide con la cantidad de personas ingresada.");
                                }
                            } else {
                                throw new VuelosException("Número de personas inválido.");
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
                .message("Reserva de vuelo dada de alta correctamente.")
                .name("CREACIÓN")
                .build();
    }

    public MessageDTO deleteFlightReservation(Integer id) {
        if (flightsBookingRepository.existsById(id)) {
            flightsBookingRepository.deleteById(id);

            return MessageDTO.builder()
                    .message("Reserva de vuelo dada de baja correctamente.")
                    .name("ELIMINACIÓN")
                    .build();

        } else {
            throw new CustomException("ELIMINACIÓN", "Reserva de vuelo con id: " + id + " no ha sido encontrada.");
        }
    }


    public MessageDTO editEntity(String flightNumber, FlightModel vueloEdit) {


        // filtramos por el código que nos envía
        List<FlightModel> listaFiltrada = flightsRepository.findByNroVuelo(flightNumber);

        //buscamos cambiar el vuelo que queremos, por lo que también
        // hay que verificar el tipo de asiento, ya que el código de vuelo a veces se repite
        FlightModel vueloSeleccionado = listaFiltrada.stream()
                .filter(vuelo -> vuelo.getTipoAsiento().equals(vueloEdit.getTipoAsiento()))
                .findFirst()
                .orElse(null);


        //si existe el vuelo, empezamos
        if (!listaFiltrada.isEmpty() && vueloSeleccionado != null) {

            //le damos al vuelo el mismo id que ya tenía en la db
            vueloEdit.setId(vueloSeleccionado.getId());

            // verificamos que se cambie alguna información
            if (vueloSeleccionado.equals(vueloEdit)) {
                throw new CustomException("MODIFICACIÓN", "Debe modificar algún dato.");
            }

            //VALIDACION FECHA ENTRADA MENOR A SALIDA
            if (vueloEdit.getFechaIda().isAfter(vueloEdit.getFechaVuelta())) {
                throw new CustomException("MODIFICACIÓN", "La fecha de ida debe ser menor a la de vuelta.");
            }

            //VALIDACION FECHA SALIDA MAYOR A ENTRADA
            if (vueloEdit.getFechaIda().isEqual(vueloEdit.getFechaVuelta())) {
                throw new CustomException("MODIFICACIÓN", "La fecha de vuelta debe ser mayor a la de ida");
            }

            //si sale bien, reemplazamos el vuelo a la db y enviamos un msj de informe

            flightsRepository.save(vueloEdit);
            return MessageDTO.builder()
                    .message("El vuelo ha sido modificado exitosamente.")
                    .name("MODIFICACIÓN")
                    .build();

        } else {
            throw new CustomException("MODIFICACIÓN", "No se ha encontrado un vuelo con el código enviado.");
        }

    }

    public List<FlightResponseDto> getAllBookings() {
        var list = flightsBookingRepository.findAll();

        if (list.isEmpty()) {
            throw new CustomException("CONSULTA", "No existen reservas.");
        }

        return list.stream().map(
                        booking -> mapper.map(booking, FlightResponseDto.class)
                )
                .collect(Collectors.toList());
    }

    public MessageDTO updateBookingByID(Integer id, FlightResponseDto bookingDto) {

        // BUSCAMOS EL BOOKING EN LA BASE DE DATOS
        if (flightsBookingRepository.existsById(id)) {
            var bookingDB = flightsBookingRepository.getById(id);
            // OBTENEMOS EL ID DEL BOOKING EN LA BASE DE DATOS
            var bookingId = bookingDB.getId();
            // OBTEBEMOS LOS DATOS DENTRO DEL BOOKING(EL FLIGHTRESERVATIONRES
            var dataBookingDB = bookingDB.getFlightReservationResModel();
            // TRANSFORMAMOS EL DTO EN MODELO
            var newBooking = mapper.map(bookingDto, FlightResponseModel.class);
            var newBookingData = mapper.map(bookingDto.getFlightReservation(), FlightReservationResModel.class);

            // ASIGNAMOS EL ID DE LA BASE DE DATOS AL NUEVO BOOKING
            newBooking.setId(bookingId);
            // REALIZAMOS CHEQUEOS MEDIANTE METODO PRIVADO
            setBooking(dataBookingDB, newBookingData);

            newBooking.setFlightReservationResModel(newBookingData);
            // RECALCULAMOS EL TOTAL CON UN METODO PRIVADO
            newBooking.setTotal(newTotal(newBooking));
            // ASIGNAMOS LAS ID DE LAS PERSONAS DE LA BASE DE DATOS A LAS PERSONAS EN EL NUEVO BOOKING
            for (int i = 0; i < newBookingData.getPeople().size(); i++) {

                var personId = dataBookingDB.getPeople().get(i).getId();

                newBookingData.getPeople().get(i).setId(personId);
            }

            var paymentMethodId = bookingDB.getPaymentMethod().getId();
            newBooking.getPaymentMethod().setId(paymentMethodId);

            flightsBookingRepository.save(newBooking);
            return MessageDTO.builder()
                    .name("MODIFICACIÓN")
                    .message("Reserva de booking de vuelo modificada correctamente")
                    .build();
        } else {
            throw new CustomException("MODIFICACIÓN", "Reserva de booking de vuelo no fue modificada");
        }

    }

    private boolean setBooking(FlightReservationResModel bookingDB, FlightReservationResModel newBooking) {

        newBooking.setId(bookingDB.getId());
        newBooking.setFlightModel(bookingDB.getFlightModel());

        if (!newBooking.getDateFrom().isEqual(bookingDB.getDateFrom())) {
            throw new CustomException("EDICIÓN", "La fecha de salida de este vuelo es única y no puede ser cambiada. Fecha: " + bookingDB.getDateFrom() + ".");
        }

        if (!newBooking.getDatoTo().isEqual(bookingDB.getDatoTo())) {
            throw new CustomException("EDICIÓN", "La fecha de vuelta de este vuelo es única y no puede ser cambiada. Fecha: " + bookingDB.getDatoTo() + ".");
        }

        if (!newBooking.getDestination().equalsIgnoreCase(bookingDB.getDestination())) {
            throw new CustomException("EDICIÓN", "El destino(destination) no coincide con el del vuelo: " + bookingDB.getDestination() + ".");
        }

        if (!newBooking.getOrigin().equalsIgnoreCase(bookingDB.getOrigin())) {
            throw new CustomException("EDICIÓN", "El origen(origin) no coincide con el del vuelo: " + bookingDB.getOrigin() + ".");
        }

        if (!newBooking.getFlightNumber().equalsIgnoreCase(bookingDB.getFlightNumber())) {
            throw new CustomException("EDICIÓN", "El código de vuelo no puede ser cambiado, debe ser: " + bookingDB.getOrigin() + ".");
        }

        if (!newBooking.getSeats().equals(bookingDB.getSeats())) {
            throw new CustomException("EDICIÓN", "No se puede agregar personas a esta reserva, solo modificar las existentes.");
        }

        return true;

    }

    private Double newTotal(FlightResponseModel booking) {
        var flight = flightsRepository.findByNroVueloAndTipoAsientoEquals(booking.getFlightReservationResModel().getFlightNumber(), booking.getFlightReservationResModel().getSeatType());
        var cardType = booking.getPaymentMethod().getType();

        if (flight == null) {
            throw new CustomException("EDICIÓN", "No existe este tipo de asiento en el vuelo: " + booking.getFlightReservationResModel().getFlightNumber());
        }

        var total = flight.getPrecioPersona() * booking.getFlightReservationResModel().getSeats();

        if (cardType.equalsIgnoreCase("debitcard")) {
            if (!booking.getPaymentMethod().getDues().equals(1)) {
                throw new CustomException("EDICIÓN", "Solo se puede realizar el pago en una(1) cuota con tarjeta de debito.");
            }
        }

        if (cardType.equalsIgnoreCase("creditcard")) {
            if (booking.getPaymentMethod().getDues() <= 3) {
                total *= 1.05;
            } else {
                total *= 1.10;
            }
        }
        return total;
    }

    public List<FlightsAvailableDto> findByLowerPrice()  {

        // buscar el dato en la base de datos y asegurarnos que exista
        var list = flightsRepository.findAllOrderByPrecioPersonaAsc();

        return list.stream().map(
                        flight -> mapper.map(flight, FlightsAvailableDto.class)
                )
                .collect(Collectors.toList());
    }

}
