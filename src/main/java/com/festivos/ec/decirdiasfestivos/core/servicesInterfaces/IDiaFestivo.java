package com.festivos.ec.decirdiasfestivos.core.servicesInterfaces;

import com.festivos.ec.decirdiasfestivos.entidades.dto.HolidayDTO;
import com.festivos.ec.decirdiasfestivos.utils.HolidayUtil;

import java.util.List;

public interface IDiaFestivo {
    String esFestivo(int ano, int dia, int mes);
    List<HolidayDTO> getHolidaysYear( int year);
}
