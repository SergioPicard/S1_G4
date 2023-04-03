package com.example.AgenciaTurismo.service.classes;

import com.example.AgenciaTurismo.dto.MessageDTO;
import com.example.AgenciaTurismo.dto.request.BookingDto;
import com.example.AgenciaTurismo.dto.request.BookingRequestDto;
import com.example.AgenciaTurismo.dto.response.BookingResDto;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.exceptions.CustomException;
import com.example.AgenciaTurismo.exceptions.SinHotelesException;
import com.example.AgenciaTurismo.models.BookingModel;
import com.example.AgenciaTurismo.models.BookingRequestModel;
import com.example.AgenciaTurismo.models.HotelModel;
import com.example.AgenciaTurismo.models.PeopleModel;
import com.example.AgenciaTurismo.repository.IBookingModelRepository;
import com.example.AgenciaTurismo.repository.IHotelBookingRepository;
import com.example.AgenciaTurismo.repository.IHotelesRepository;
import com.example.AgenciaTurismo.service.generics.ICrudService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelesService implements ICrudService<HotelAvailableDto,Integer,String> {
    @Autowired
    IHotelesRepository hotelesRepository;

    @Autowired
    IHotelBookingRepository hotelBookingRepository;

    @Autowired
    IBookingModelRepository bookingModelRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public HotelAvailableDto saveEntity(HotelAvailableDto hotelDTO) {
        // mappear de dto a entity para llevar al repo
        var entity = mapper.map(hotelDTO, HotelModel.class);
        // guardar
        hotelesRepository.save(entity);
        // mappear de entity a dto para llevar al controller
        return mapper.map(entity, HotelAvailableDto.class);
    }

    @Override
    public List<HotelAvailableDto> getAllEntities() {
        var list = hotelesRepository.findAll();
        return list.stream().map(
                        hotel -> mapper.map(hotel, HotelAvailableDto.class)
                )
                .collect(Collectors.toList());
    }

    public MessageDTO deleteEntity(String code) {
        // buscar el dato en la base de datos y asegurarnos que exista
        List<HotelModel> exists = hotelesRepository.findByCodigoHotel(code);
        // eliminar efectivamente
        if(!exists.isEmpty())
            hotelesRepository.deleteAll();
        else
            throw new CustomException("ELIMINACIÓN", "RNo se pudo encontrar el hotel con código: " + code);

        // devolver el mensaje DTO
        return MessageDTO.builder()
                .message("Se elimino el Hotel con el código: " + code)
                .name("ELIMINACIÓN")
                .build();
    }

    public List<HotelAvailableDto> filterEntity(LocalDate dateFrom, LocalDate dateTo, String destination) {
        // buscar el dato en la base de datos y asegurarnos que exista
        List<HotelModel> list = hotelesRepository.findByDisponibleDesdeLessThanEqualAndDisponibleHastaGreaterThanEqualAndLugar(dateFrom, dateTo, destination);

        if (!list.isEmpty()){
        return list.stream().map(
                        hotel -> mapper.map(hotel, HotelAvailableDto.class)
                )
                .collect(Collectors.toList());
        } else{
            throw new RuntimeException("No hay hoteles en este lugar, en estas fechas");
        }
    }


    public MessageDTO bookingResponse(BookingRequestDto bookingRequest){

        BookingRequestModel reservationHotel = new BookingRequestModel();

        //BÚSQUEDA DEL HOTEL POR CÓDIGO PASADO EN EL REQUEST.
        List<HotelModel> hotels = hotelesRepository.findByCodigoHotel(bookingRequest.getBooking().getHotelCode());

        if(hotels.isEmpty()){
            throw new SinHotelesException("El hotel que desea reservar no existe.");
        }

        //BÚSQUEDA DE HOTEL POR CÓDIGO Y HABITACIÓN
        var bookedHotel = hotelesRepository.findBycodigoHotelAndTipoHabitacionEquals(bookingRequest.getBooking()
                .getHotelCode(),bookingRequest.getBooking().getRoomType());

        if(bookedHotel == null){
            List<String> room = new ArrayList<>();
            for (HotelModel hotel : hotels) {
                room.add(hotel.getTipoHabitacion());
            }
            throw new SinHotelesException("No poseemos este tipo de habitación en el hotel seleccionado. Le podemos ofrecer una habitación "
                    + room.get(0) + ".");
        }

        //VALIDACIONES
        String roomSelect = bookingRequest.getBooking().getRoomType().toUpperCase();

        int peopleAmount = bookingRequest.getBooking().getPeopleAmount();
        int people = bookingRequest.getBooking().getPeople().size();

        boolean destination = bookedHotel.getLugar().equalsIgnoreCase(bookingRequest.getBooking().getDestination());

        boolean dateFrom = bookedHotel.getDisponibleDesde().isAfter(bookingRequest.getBooking().getDateFrom());
        boolean dateTo = bookedHotel.getDisponibleHasta().isBefore(bookingRequest.getBooking().getDatoTo());

        boolean dateEqualFrom = bookedHotel.getDisponibleDesde().isEqual(bookingRequest.getBooking().getDateFrom());
        boolean dateEqualTo = bookedHotel.getDisponibleHasta().isEqual(bookingRequest.getBooking().getDatoTo());

        LocalDate dateFromReq = bookingRequest.getBooking().getDateFrom();
        LocalDate dateToReq = bookingRequest.getBooking().getDatoTo();


        if (!bookingRequest.getUserName().isEmpty()) {
            //VERIFICAMOS FECHA DE ENTRADA MENOR A SALIDA
            if (!dateFromReq.isAfter(dateToReq)) {

                //VERIFICAMOS FECHA DE SALIDA MENOR A ENTRADA
                if (!dateFromReq.isEqual(dateToReq)) {

                    //VERIFICAMOS DISPONIBILIDAD EN ESAS FECHAS
                    if (!dateFrom && !dateTo || dateEqualFrom && dateEqualTo) {

                        //VERIFICAMOS DE QUE EL DESTINO SOLICITADO ESTÉ EN EL MISMO LUGAR QUE EL HOTEL
                        if (destination) {

                            // SI ES MÁS DE UNA PERSONA, SEGUIMOS
                            if (peopleAmount != 0) {

                                switch (roomSelect) {
                                    //INICIAMOS SWITCH PARA LOS 4 TIPO DE HABITACIONES EXISTENTES ENTRE TODOS LOS HOTELES
                                    case "SINGLE": {
                                        if (peopleAmount > 1) {
                                            throw new SinHotelesException("No puede ingresar " + peopleAmount + " personas en una habitación tipo Single.");
                                        }
                                        break;
                                    }
                                    case "DOBLE": {
                                        if (peopleAmount > 2) {
                                            throw new SinHotelesException("No puede ingresar " + peopleAmount + " personas en una habitación tipo Doble.");
                                        }
                                        break;
                                    }
                                    case "TRIPLE": {
                                        if (peopleAmount > 3) {
                                            throw new SinHotelesException("No puede ingresar " + peopleAmount + " personas en una habitación tipo Triple.");
                                        }
                                        break;
                                    }
                                    case "MÚLTIPLE": {
                                        if (peopleAmount > 4) {
                                            throw new SinHotelesException("El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella. " +
                                                    "No puede ingresar " + peopleAmount + " personas en una habitación tipo Múltiple (máximo 4).");
                                        }
                                        break;
                                    }
                                }

                                if (peopleAmount == people) {
                                    // SI COINCIDE LA CANTIDAD DE HUÉSPEDES CON LA CANTIDAD DE PERSONAS INGRESADA

                                    double bookingDays = bookingRequest.getBooking().getDatoTo().getDayOfYear() - bookingRequest.getBooking().getDateFrom().getDayOfYear();
                                    //CALCULO DE CANTIDAD DE DIAS DE DIF

                                    if (bookingRequest.getBooking().getPaymentMethod().getType().equalsIgnoreCase("debitcard")) {

                                        bookingDays = bookingRequest.getBooking().getDatoTo().getDayOfYear() - bookingRequest.getBooking().getDateFrom().getDayOfYear();

                                        if (bookingRequest.getBooking().getPaymentMethod().getDues() > 1) {
                                            throw new SinHotelesException("El método de pago es Débito, solo puede elegir 1 cuota.");
                                        }
                                    }

                                    double total = bookedHotel.getPrecioNoche() * bookingDays;

                                    if (bookingRequest.getBooking().getPaymentMethod().getType().equalsIgnoreCase("creditcard")) {
                                        int cuotas = bookingRequest.getBooking().getPaymentMethod().getDues();
                                        if (cuotas <= 3) {
                                            total *= 1.05;

                                        } else {
                                            total *= 1.10;

                                        }
                                    }
                                    //MAP DEL BOOKING DTO A BOOKING MODEL
                                    BookingModel booking = mapper.map(bookingRequest.getBooking(),BookingModel.class);
                                    booking.setHotelModel(bookedHotel);
                                    booking.setTotal(total);

                                    //SET DEL RESPONSE
                                    reservationHotel.setBooking(booking);
                                    reservationHotel.setUserName(bookingRequest.getUserName());

                                } else {
                                    throw new SinHotelesException("El número de huéspedes no coincide con la cantidad de personas ingresada.");

                                }
                            } else {
                                throw new SinHotelesException("No puede colocar 0 en las personas que ingresarán a la habitación.");

                            }

                        } else {
                            throw new SinHotelesException("El Hotel '" + bookedHotel.getNombre() + "' se encuentra en " + bookedHotel.getLugar() + ", no en "
                                    + bookingRequest.getBooking().getDestination() + ". El destino elegido no existe");

                        }
                    } else {
                        throw new SinHotelesException("El Hotel '" + bookedHotel.getNombre() + "' se encuentra dispobile desde el " + bookedHotel.getDisponibleDesde() + " hasta el "
                                + bookedHotel.getDisponibleHasta());
                    }
                } else {
                    throw new SinHotelesException("La fecha de salida debe ser mayor a la de entrada");
                }
            } else {
                throw new SinHotelesException("La fecha de entrada debe ser menor a la de salida");
            }
        } else {
            throw new SinHotelesException("Debe ingresar un usuario.");

        }

        hotelBookingRepository.save(reservationHotel);

        return MessageDTO.builder()
                .message("Reserva de hotel dada de alta correctamente." )
                .name("CREACIÓN")
                .build();
    }

    public MessageDTO deleteHotelReservation(Integer id){
        if(hotelBookingRepository.existsById(id)){
            hotelBookingRepository.deleteById(id);
            bookingModelRepository.deleteById(id);


            return MessageDTO.builder()
                    .message("Reserva de vuelo dada de baja correctamente." )
                    .name("ELIMINACIÓN")
                    .build();
        }

        throw new CustomException("ELIMINACIÓN", "Reserva de hotel con id: " + id + " no ha sido encontrada.");
    }



    @Override
    public HotelAvailableDto getEntityById(Integer integer) {
        return null;
    }

    public MessageDTO editEntity(String hotelCode, HotelModel hotelEdit){

        // buscamos el hotel a editar por parámetro
        List<HotelModel> listaFiltrada = hotelesRepository.findByCodigoHotel(hotelCode);

        //listamos todos los hoteles
        List<HotelModel> listAll = hotelesRepository.findAll();

        //listamos los códigos de todos los hoteles
        List<String> hotelCodes = listAll.stream().map(HotelModel::getCodigoHotel).collect(Collectors.toList());

        //comprobamos que no exista un hotel con el mismo código al que estamos modificando
        boolean codeExist = hotelCodes.stream().anyMatch(hotelEdit.getCodigoHotel()::equalsIgnoreCase);

        //si existe el hotel, empezamos
        if (!listaFiltrada.isEmpty()) {

            //le damos al hotel el mismo id que ya tenía en la db
            hotelEdit.setId(listaFiltrada.get(0).getId());

            // filtramos el primer elemento de la lista, la cual solo contiene uno ya q filtra por código de hotel
            if (listaFiltrada.get(0).equals(hotelEdit)) {
                throw new CustomException("MODIFICACIÓN","Debe modificar algún dato.");
            }

            // si cambiamos el código y el mismo no se repite en la db, se lo crea
            if (!codeExist) {
                hotelesRepository.save(hotelEdit);
                return MessageDTO.builder()
                        .message("El hotel ha sido modificado exitosamente.")
                        .name("MODIFICACIÓN")
                        .build();
            } else {
                throw new CustomException("MODIFICACIÓN","Ya existe un hotel con el mismo código de hotel ingresado.");
            }

            }else {
                throw new CustomException("MODIFICACIÓN", "No se ha encontrado un hotel con el código enviado.");
            }
            }

    public List<BookingResDto> getAllBookings() {
        var list = bookingModelRepository.findAll();
        return list.stream().map(
                        booking -> mapper.map(booking, BookingResDto.class)
                )
                .collect(Collectors.toList());
    }

    public MessageDTO updateBookingByID(Integer id, BookingDto bookingDto){

        if (bookingModelRepository.existsById(id)) {

            var model = bookingModelRepository.getById(id);
            var entity = mapper.map(bookingDto, BookingModel.class);
            var hotel = hotelesRepository.findByCodigoHotel(model.getHotelCode()).get(0);

            validationsBooking(entity, hotel);

            if(!entity.getPeopleAmount().equals(model.getPeopleAmount())){

                if(peopleAmountFitInRoom(entity)){
                    throw new CustomException("EDICIÓN", "La cantidad de personas no puede ser mayor a: "+maxPersonsPerRoom(entity)+ ".");
                }
            }

            if(entity.getPeopleAmount() != entity.getPeople().size()){
                throw new CustomException("EDICIÓN", "La cantidad de personas ingresadas no coincide con la estipulada");

            }
            for (int i = 0; i < model.getPeople().size(); i++) {
                var personId = model.getPeople().get(i).getId();
                entity.getPeople().get(i).setId(personId);
            }


            entity.setId(id);
            entity.setTotal(model.getTotal());
            entity.setHotelModel(model.getHotelModel());

            var paymentId = model.getPaymentMethod().getId();
            entity.getPaymentMethod().setId(paymentId);
            //probar no crear nuevas personas si ya existen en la base de datos
            // crear un Irepository de personas, y buscarlas por id? o crear metodo nombrado para buscar?
            //que pasa si cambio el numero de personas?

            bookingModelRepository.save(entity);
            return MessageDTO.builder()
                    .name("MODIFICACION")
                    .message("Reserva de hotel modificada correctamente")
                    .build();
        } else {
            return MessageDTO.builder()
                    .name("MODIFICACION")
                    .message("No se pudo encontrar la reserva especificada")
                    .build();
        }
    }

    private Boolean validationsBooking(BookingModel entity, HotelModel hotel){
        if(!entity.getRoomType().equals(hotel.getTipoHabitacion())){
            throw new CustomException("EDICIÓN", "Tipo de habitacion invalido debe ser: "+hotel.getTipoHabitacion()+ ".");
        }
        if(!entity.getHotelCode().equals(hotel.getCodigoHotel())){
            throw new CustomException("EDICIÓN", "El codigo de hotel es invalido debe ser: "+hotel.getCodigoHotel()+ ".");
        }
        if(!entity.getDestination().equals(hotel.getLugar())) {
            throw new CustomException("EDICIÓN", "Destino invalido, debe ser: "+hotel.getLugar()+ ".");
        }
        if(entity.getDateFrom().isBefore(hotel.getDisponibleDesde()) ) {
            throw new CustomException("EDICIÓN", "La fecha es incorrecta, debe ser posterior a : "+hotel.getDisponibleDesde()+ ".");
        }
        if(entity.getDatoTo().isAfter(hotel.getDisponibleHasta()) ) {
            throw new CustomException("EDICIÓN", "La fecha es incorrecta, debe ser anterior a : "+hotel.getDisponibleHasta()+ ".");
        }
        if(maxPersonsPerRoom(entity) > entity.getPeopleAmount()){
            throw new CustomException("EDICIÓN", "La cantidad de personas no puede ser mayor a: "+maxPersonsPerRoom(entity)+ ".");
        }
        return true;
    }

    private Integer maxPersonsPerRoom(BookingModel booking){

        Integer maxAmout = 0;

        switch (booking.getRoomType()) {
            //INICIAMOS SWITCH PARA LOS 4 TIPO DE HABITACIONES EXISTENTES ENTRE TODOS LOS HOTELES
            case "SINGLE": {
                maxAmout = 1;
                break;
            }
            case "DOBLE": {
                maxAmout = 2;
                break;
            }
            case "TRIPLE": {
                maxAmout = 3;
                break;
            }
            case "MÚLTIPLE": {
                maxAmout = 4;
                break;
            }
        }

        return maxAmout;
    }

    private Boolean peopleAmountFitInRoom(BookingModel booking){
        var peopleFit = false;

        switch (booking.getRoomType()) {
            //INICIAMOS SWITCH PARA LOS 4 TIPO DE HABITACIONES EXISTENTES ENTRE TODOS LOS HOTELES
            case "SINGLE": {
                if (booking.getPeopleAmount() > 1) {
                    peopleFit = true;
                }
                break;
            }
            case "DOBLE": {
                if (booking.getPeopleAmount() > 2) {
                    peopleFit = true;
                }
                break;
            }
            case "TRIPLE": {
                if (booking.getPeopleAmount() > 3) {
                    peopleFit = true;
                }
                break;
            }
            case "MÚLTIPLE": {
                if (booking.getPeopleAmount() > 4) {
                    peopleFit = true;
                }
                break;
            }
        }

        return peopleFit;
    }

    }

    /*
    public List<HotelAvailableDto> filterHotels(LocalDate dateFrom, LocalDate dateTo, String destination) {

        List<HotelAvailableDto> allHotels = hotelesRepository.findAll();

        List<HotelAvailableDto> destinationStatus = allHotels.stream().filter(hotel -> Objects.equals(hotel.getLugar(), destination)).collect(Collectors.toList());
        List<HotelAvailableDto> dateFromStatus = destinationStatus.stream().filter(hotel -> hotel.getDisponibleDesde().isAfter(dateFrom)).collect(Collectors.toList());
        List<HotelAvailableDto> dateToStatus = destinationStatus.stream().filter(hotel -> hotel.getDisponibleHasta().isBefore(dateTo)).collect(Collectors.toList());
        List<HotelAvailableDto> dateEqualFromStatus = destinationStatus.stream().filter(hotel -> hotel.getDisponibleDesde().equals(dateFrom)).collect(Collectors.toList());
        List<HotelAvailableDto> dateEqualToStatus = destinationStatus.stream().filter(hotel -> hotel.getDisponibleHasta().equals(dateTo)).collect(Collectors.toList());



        // VALIDACION POR DESTINO
        if (destinationStatus.isEmpty()){
            throw new SinHotelesException("El destino elegido no existe.");
        }

        //VALIDACION FECHA ENTRADA MENOR A SALIDA
        if(dateFrom.isAfter(dateTo)){
            throw new SinHotelesException("La fecha de ida debe ser menor a la de vuelta.");
        }

        //VALIDACION FECHA SALIDA MAYOR A ENTRADA
        if(dateFrom.isEqual(dateTo)){
            throw new SinHotelesException("La fecha de vuelta debe ser mayor a la de ida");
        }

        List<HotelAvailableDto> hotelAvailable = hotelesRepository.filterHotelsRep(dateFrom, dateTo, destination);

        if(hotelAvailable.isEmpty()){
            throw new SinHotelesException("No se encontraron hoteles disponibles en esta fecha.");
        }

        return hotelAvailable;
    }
*/


