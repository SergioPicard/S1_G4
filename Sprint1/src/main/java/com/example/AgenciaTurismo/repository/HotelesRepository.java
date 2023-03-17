package com.example.AgenciaTurismo.repository;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.exceptions.SinHotelesException;
import com.example.AgenciaTurismo.models.FlightModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.example.AgenciaTurismo.dto.response.HotelAvailableDto;
import com.example.AgenciaTurismo.models.HotelModel;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class HotelesRepository implements IHotelesRepository{
    List<HotelAvailableDto> hotelsAvailable = new ArrayList<>();


    public HotelesRepository(){
        dataBase();
    }

    public List<HotelModel> dataBase() {
        List<HotelModel> hoteles = null;

        File file;
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule());
        TypeReference<List<HotelModel>> typeRef = new TypeReference<>() {};

        try {
            file = ResourceUtils.getFile("classpath:JSON/hoteles.json");
            hoteles = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert hoteles != null;
        for (HotelModel hotel : hoteles) {
            hotelsAvailable.add(new HotelAvailableDto(hotel.getCodigoHotel(),hotel.getNombre(),hotel.getLugar(),hotel.getTipoHabitacion(),
                    hotel.getPrecioNoche(),hotel.getDisponibleDesde(),hotel.getDisponibleHasta(),hotel.getReservado()));
        }

        return hoteles;

    }

    public List<HotelAvailableDto> findAll(){
        return hotelsAvailable;
    }

    public List<HotelAvailableDto> filterHotelsRep(LocalDate dateFrom, LocalDate dateTo, String destination){

        List<HotelAvailableDto> allHotels = findAll();
        List<HotelAvailableDto> destinationStatus = allHotels.stream().filter(hotel -> Objects.equals(hotel.getLugar(), destination)).collect(Collectors.toList());

        List<HotelAvailableDto> dateFromStatus = destinationStatus.stream().filter(hotel -> hotel.getDisponibleDesde().isAfter(dateFrom)).collect(Collectors.toList());
        List<HotelAvailableDto> dateToStatus = destinationStatus.stream().filter(hotel -> hotel.getDisponibleHasta().isBefore(dateTo)).collect(Collectors.toList());
        List<HotelAvailableDto> dateEqualFromStatus = destinationStatus.stream().filter(hotel -> hotel.getDisponibleDesde().equals(dateFrom)).collect(Collectors.toList());
        List<HotelAvailableDto> dateEqualToStatus = destinationStatus.stream().filter(hotel -> hotel.getDisponibleHasta().equals(dateTo)).collect(Collectors.toList());

        // VALIDACION POR DESTINO
        if (destinationStatus.isEmpty()){
            throw new SinHotelesException("El destino elegido no existe.");
        }
        //VALIDACION POR FECHA
        if (!dateFromStatus.isEmpty() && !dateToStatus.isEmpty() || dateEqualFromStatus.isEmpty() && dateEqualToStatus.isEmpty()) {
            throw new SinHotelesException("No se encontraron hoteles disponibles en esta fecha.");
        }

        return hotelsAvailable.stream().filter(hotel -> hotel.getLugar().equalsIgnoreCase(destination) &&
                !hotel.getDisponibleDesde().isAfter(dateFrom) &&
                !hotel.getDisponibleHasta().isBefore(dateTo) &&
                !hotel.getReservado()).collect(Collectors.toList());
    }

    public HotelAvailableDto findHotel(String code){
        return hotelsAvailable.stream().filter(hotel -> hotel.getCodigoHotel().equalsIgnoreCase(code))
                .findFirst().orElse(null);

    }

}
