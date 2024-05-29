package com.festivos.ec.decirdiasfestivos.entidades.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDTO {
    private String dia;
    private String mes;
    private String nombre;
}
