package com.example.AgenciaTurismo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultasNuevasFuncionalidades {

    public static List<Map<String, Object>> listSold(){

        Map<String, Object> resultado = new HashMap<>();

        resultado.put("Vuelo", "LUCHO-1992");
        resultado.put("TotalRecaudación", 10000.0);

        return List.of(resultado);

    }

    public static List<Map<String, Object>> listSoldIntegration(){

        Map<String, Object> resultado = new HashMap<>();

        resultado.put("Vuelo", "BAPI-1235");
        resultado.put("TotalRecaudación", 6500.0);

        return List.of(resultado);

    }


}
