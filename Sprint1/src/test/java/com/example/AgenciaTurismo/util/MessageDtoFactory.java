package com.example.AgenciaTurismo.util;

import com.example.AgenciaTurismo.dto.MessageDTO;

public class MessageDtoFactory {

    public static MessageDTO altaVuelo(){
        return MessageDTO.builder()
                .message("Vuelo dado de alta correctamente.")
                .name("CREACIÓN")
                .build();
    }
}
