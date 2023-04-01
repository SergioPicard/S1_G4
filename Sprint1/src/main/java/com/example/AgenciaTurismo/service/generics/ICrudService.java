package com.example.AgenciaTurismo.service.generics;

import com.example.AgenciaTurismo.dto.MessageDTO;

import java.util.List;

public interface ICrudService<T, ID,String> {

    T saveEntity(T objectDTO);

    List<T> getAllEntities();

    T getEntityById(ID id);

    MessageDTO deleteEntity(String string);
}
