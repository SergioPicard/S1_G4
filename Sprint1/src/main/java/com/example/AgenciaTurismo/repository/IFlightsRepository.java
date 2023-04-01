package com.example.AgenciaTurismo.repository;

<<<<<<< HEAD
import com.example.AgenciaTurismo.models.FlightModel;
=======
import com.example.AgenciaTurismo.dto.request.FlightReservationReqDto;
import com.example.AgenciaTurismo.dto.response.FlightResponseDto;
import com.example.AgenciaTurismo.dto.response.FlightsAvailableDto;
import com.example.AgenciaTurismo.models.FlightModel;
import com.example.AgenciaTurismo.models.FlightReservationReqModel;
import com.example.AgenciaTurismo.models.FlightReservationResModel;
import com.example.AgenciaTurismo.models.HotelModel;
>>>>>>> camila_sapino
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IFlightsRepository extends JpaRepository<FlightModel,Integer> {

    List<FlightModel> findByNroVuelo(String codigo);

    List<FlightModel> findByFechaIdaAndFechaVueltaAndAndOrigenAndDestino
            (LocalDate dateFrom, LocalDate dateTo,String origen, String destination);

    FlightModel findByNroVueloAndTipoAsientoEquals(String code, String asiento);


/*    List<FlightModel> dataBase();

    List<FlightsAvailableDto> findAll();

    List<FlightsAvailableDto> filterFlightRep(LocalDate fechaIda, LocalDate fechaVuelta, String origen, String destino);

    FlightsAvailableDto findFlight(String flightNumber, String seatType);*/

}
