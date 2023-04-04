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
import java.util.stream.Stream;

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
        System.out.println(exists);

        // eliminar efectivamente
        if(!exists.isEmpty()){
            if(!bookingModelRepository.findByHotelCode(code).isEmpty()){
                throw new CustomException("ELIMINACIÓN", "Existe una reserva con dicho hotel. Cancelar la reserva antes de eliminar el hotel.");
            }else {
                for (HotelModel hotel : exists) {
                    hotelesRepository.delete(hotel);
                }
            }
        }else{
            throw new CustomException("ELIMINACIÓN", "RNo se pudo encontrar el hotel con código: " + code);
        }

        // devolver el mensaje DTO
        return MessageDTO.builder()
                .message("Se elimino el Hotel con el código: " + code)
                .name("ELIMINACIÓN")
                .build();
    }

    public List<HotelAvailableDto> filterEntity(LocalDate dateFrom, LocalDate dateTo, String destination) {
        // buscar el dato en la base de datos y asegurarnos que exista
        List<HotelModel> list = hotelesRepository.findByDisponibleDesdeLessThanEqualAndDisponibleHastaGreaterThanEqualAndLugar(dateFrom, dateTo, destination);

        if (dateFrom.isAfter(dateTo) || dateFrom.equals(dateTo)){
            throw new CustomException("FILTRAR","La fecha de ingreso debe ser menor a la de salida.");
        }

        if (!destination.equalsIgnoreCase(list.stream().map(HotelModel::getLugar).toString())){
        throw new CustomException("FILTRAR","No hay hoteles en el destino elegido.");
        }

        if (!list.isEmpty()){
        return list.stream().map(
                        hotel -> mapper.map(hotel, HotelAvailableDto.class)
                )
                .collect(Collectors.toList());
        } else{
            throw new CustomException("FILTRAR","No hay hoteles en estas fechas.");
        }
    }


    public MessageDTO bookingResponse(BookingRequestDto bookingRequest){

        BookingRequestModel reservationHotel = new BookingRequestModel();

        //BÚSQUEDA DEL HOTEL POR CÓDIGO PASADO EN EL REQUEST.
        List<HotelModel> hotels = hotelesRepository.findByCodigoHotel(bookingRequest.getBooking().getHotelCode());

        if(hotels.isEmpty()) throw new SinHotelesException("El hotel que desea reservar no existe.");

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
        if (!bookingRequest.getUserName().isEmpty()) {

            //VERIFICAMOS FECHA DE ENTRADA MENOR A SALIDA
            if (!bookingRequest.getBooking().getDateFrom().isAfter(bookingRequest.getBooking().getDatoTo())) {

                //VERIFICAMOS FECHA DE SALIDA MENOR A ENTRADA
                if (!bookingRequest.getBooking().getDateFrom().isEqual(bookingRequest.getBooking().getDatoTo())) {

                    //VERIFICAMOS DISPONIBILIDAD EN ESAS FECHAS
                    if (compareDate(bookedHotel,bookingRequest)) {

                        //VERIFICAMOS DE QUE EL DESTINO SOLICITADO ESTÉ EN EL MISMO LUGAR QUE EL HOTEL
                        if (bookedHotel.getLugar().equalsIgnoreCase(bookingRequest.getBooking().getDestination())) {

                            // SI ES MÁS DE UNA PERSONA, SEGUIMOS
                            if (bookingRequest.getBooking().getPeopleAmount() != 0) {

                                peopleAmountInBookingCreate(bookingRequest);

                                if (bookingRequest.getBooking().getPeopleAmount() == bookingRequest.getBooking().getPeople().size()) {
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

            if(entity.getPeopleAmount() != entity.getPeople().size() || !entity.getPeopleAmount().equals(model.getPeopleAmount())){
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
                    .name("MODIFICACIÓN")
                    .message("Reserva de hotel modificada correctamente")
                    .build();
        } else {
            throw new CustomException("MODIFICACIÓN", "No se pudo encontrar la reserva especificada");
        }
    }

    private Boolean compareDate(HotelModel hotel, BookingRequestDto request){
        boolean dateFrom = hotel.getDisponibleDesde().isAfter(request.getBooking().getDateFrom());
        boolean dateTo = hotel.getDisponibleHasta().isBefore(request.getBooking().getDatoTo());

        boolean dateEqualFrom = hotel.getDisponibleDesde().isEqual(request.getBooking().getDateFrom());
        boolean dateEqualTo = hotel.getDisponibleHasta().isEqual(request.getBooking().getDatoTo());

        return !dateFrom && !dateTo || dateEqualFrom && dateEqualTo;
    }

    private void peopleAmountInBookingCreate(BookingRequestDto bookingRequest){
        Integer peopleFit = bookingRequest.getBooking().getPeopleAmount();

        switch (bookingRequest.getBooking().getRoomType().toUpperCase()) {
            //INICIAMOS SWITCH PARA LOS 4 TIPO DE HABITACIONES EXISTENTES ENTRE TODOS LOS HOTELES
            case "SINGLE": {
                if (peopleFit > 1) {
                    throw new SinHotelesException("No puede ingresar " + peopleFit + " personas en una habitación tipo Single.");
                }
                break;
            }
            case "DOBLE": {
                if (peopleFit > 2) {
                    throw new SinHotelesException("No puede ingresar " + peopleFit + " personas en una habitación tipo Doble.");
                }
                break;
            }
            case "TRIPLE": {
                if (peopleFit > 3) {
                    throw new SinHotelesException("No puede ingresar " + peopleFit + " personas en una habitación tipo Triple.");
                }
                break;
            }
            case "MÚLTIPLE": {
                if (peopleFit > 4) {
                    throw new SinHotelesException("El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella. " +
                            "No puede ingresar " + peopleFit + " personas en una habitación tipo Múltiple (máximo 4).");
                }
                break;
            }
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



    // ENDPOINTS NUEVOS


    public List<HotelAvailableDto> filterEntityForDestination(String destination)  {

            // buscar el dato en la base de datos y asegurarnos que exista
            List<HotelModel> list = hotelesRepository.findByLugar(destination);

            if (list.isEmpty()) {
                throw new CustomException("FILTRAR", "No se han encontrado hoteles en el lugar elegido.");
            }

            return list.stream().map(
                    hotel -> mapper.map(hotel, HotelAvailableDto.class)
            ).collect(Collectors.toList());
        }

    public List<HotelAvailableDto> findByLowerPrice()  {

        // buscar el dato en la base de datos y asegurarnos que exista
        var list = hotelesRepository.findAllOrderByPrecioNocheAsc();

        return list.stream().map(
                        hotel -> mapper.map(hotel, HotelAvailableDto.class)
                )
                .collect(Collectors.toList());
    }

    public MessageDTO staySimulation(String hotelCode, Integer days, Integer peopleAmount,
                                     String pay, Integer seat) {

        // buscar el dato en la base de datos y asegurarnos que exista
        List<HotelModel> list = hotelesRepository.findByCodigoHotel(hotelCode);
        System.out.println(list);



        if (pay.equalsIgnoreCase("credit") || pay.equalsIgnoreCase("debit")){

        // si no encontramos el hotel solicitado:
        if (list.isEmpty()) {
            throw new CustomException("COSTO SIMULACIÓN", "Código de hotel incorrecto.");
        }
        // si se envía 0 o un número negativo en los días o cantidad de personas
        if (days <= 0 || peopleAmount <= 0) {
            throw new CustomException("COSTO SIMULACIÓN", "Debe ingresar un número mayor a 0");
        }


        // buscamos el precio del hotel encontrado
        double priceHotel = list.stream()
                .filter(hotel -> hotel.getPrecioNoche() != null)
                .mapToDouble(HotelModel::getPrecioNoche)
                .findFirst()
                .orElse(0.0);
        // calculamos el total
        var result = (priceHotel * days) * peopleAmount;


        // si es débido, lo devolvemos inmediatamente
        if (pay.equalsIgnoreCase("debit")){
            return MessageDTO.builder()
                    .message("El costo abonando con tarjeta de débito para " +peopleAmount+ " personas, durante " + days + " días, en el hotel código: " + hotelCode
                            + ", es de: $" + result)
                    .name("COSTO SIMULACIÓN")
                    .build();
        }

        // en caso de ser crédito, se le suma un recargo
        if (pay.equalsIgnoreCase("credit")) {

            if (seat <= 0){
                throw new CustomException("COSTO SIMULACIÓN", "Debe ingresar una cuota mayor a 0");
            }

            if (seat <= 3) {
                result *= 1.05;

            } else if (seat <= 6){
                result *= 1.10;
            }
            else {
                throw new CustomException("COSTO SIMULACIÓN", "El número de cuotas es de 1 hasta 6.");
            }
        }

        return MessageDTO.builder()
                .message("El costo abonando con tarjeta de crédito (incluye recargo) para " +peopleAmount+ " personas, durante "
                        + days + " días, en el hotel código: " + hotelCode
                        + ", es de: $" + result + " realizado en "
                        + seat + " cuotas, con un costo de c/u: $" + (result/seat))
                .name("COSTO SIMULACIÓN")
                .build();
        } else{
            throw new CustomException("COSTO SIMULACIÓN", "Debe ingresar debit/credit en el metodo de pago.");
        }
    }

    public List<HotelAvailableDto> filterByName(String hotelName)  {

        // buscar el dato en la base de datos y asegurarnos que exista
        List<HotelModel> list = hotelesRepository.findByNombreContainsIgnoreCase(hotelName);
        System.out.println(list);

        if (list.isEmpty()) {
            throw new CustomException("FILTRAR", "No se han encontrado hoteles en el lugar elegido.");
        }

        return list.stream().map(
                hotel -> mapper.map(hotel, HotelAvailableDto.class)
        ).collect(Collectors.toList());
    }










    }
