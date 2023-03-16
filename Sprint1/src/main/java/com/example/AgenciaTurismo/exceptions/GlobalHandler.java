package com.example.AgenciaTurismo.exceptions;

import com.example.AgenciaTurismo.dto.ErrorDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(RuntimeException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<String> handlerRuntime(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(SinHotelesException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<String> SinHotelesException(SinHotelesException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(VuelosException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<String> VuelosException(VuelosException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CapacidadMaximaException.class)
    //Spring sabe que si sale una exception de este tipo, se ejecuta el metodo abajo.
    public ResponseEntity<String> CapacidadMaximaException(CapacidadMaximaException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

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

