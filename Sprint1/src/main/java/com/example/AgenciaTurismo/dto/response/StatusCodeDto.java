package com.example.AgenciaTurismo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatusCodeDto {
    private Integer code;
    private String message;
}
