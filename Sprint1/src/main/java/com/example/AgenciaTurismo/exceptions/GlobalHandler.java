package com.example.AgenciaTurismo.exceptions;

import com.example.AgenciaTurismo.dto.ErrorDTO;
import com.example.AgenciaTurismo.dto.MessageDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(InvalidFormatException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<MessageDTO> parametrosError(InvalidFormatException e) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setName("Formato de fecha invalido");
        messageDTO.setMessage("Formato de fecha debe ser dd/mm/aaaa");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<MessageDTO> parametrosError(MethodArgumentTypeMismatchException e) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setName("Formato de fecha invalido");
        messageDTO.setMessage("Formato de fecha debe ser dd/mm/aaaa");
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SinHotelesException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<MessageDTO> parametrosError(SinHotelesException e) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setName("Validación en hoteles.");
        messageDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<MessageDTO> parametrosError(CustomException ex) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(ex.getMessage());
        messageDTO.setName(ex.getName());
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(VuelosException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<MessageDTO> parametrosError(VuelosException e) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setName("Validación en vuelos.");
        messageDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CarsException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<MessageDTO> parametrosError(CarsException e) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setName("Validación en autos.");
        messageDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }

    // AMBOS METODOS SIRVEN PARA CONVERTIR LOS MENSAJES DE LAS VALIDACIONES EN UN ARRAY DE MENSAJES ENTENDIBLES.

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> validationException(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(
                new ErrorDTO("Se encontraron los siguientes errores en las validaciones: ",
                        e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList())
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> validationException(ConstraintViolationException e){
        return ResponseEntity.badRequest().body(
                new ErrorDTO("Se encontraron los siguientes errores en las validaciones: ",
                        e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList())
                )
        );
    }
}

