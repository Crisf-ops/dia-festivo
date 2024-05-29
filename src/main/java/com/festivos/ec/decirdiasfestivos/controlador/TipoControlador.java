package com.festivos.ec.decirdiasfestivos.controlador;

import com.festivos.ec.decirdiasfestivos.entidades.dto.HolidayDTO;
import com.festivos.ec.decirdiasfestivos.services.DiaFestivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TipoControlador {

    DiaFestivoServicio diaFestivoServicio;

    @Autowired
    public TipoControlador( DiaFestivoServicio diaFestivoServicio ) {
        this.diaFestivoServicio = diaFestivoServicio;
    }

    @GetMapping("/esFestivo")
    public String getAll(@RequestParam("ano") int ano,
                             @RequestParam("mes") int mes,
                             @RequestParam("dia") int dia) {
        return diaFestivoServicio.esFestivo(ano, dia, mes);
    }

    @GetMapping("/getHolidaysYear")
    public List<HolidayDTO> getHolidaysYear( @RequestParam("ano") int ano) {
        return diaFestivoServicio.getHolidaysYear(ano);
    }
}
