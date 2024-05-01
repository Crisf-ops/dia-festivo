package com.festivos.ec.decirdiasfestivos.controlador;

import com.festivos.ec.decirdiasfestivos.services.DiaFestivoServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TipoControlador {

    DiaFestivoServicio diaFestivoServicio;

    public TipoControlador( DiaFestivoServicio diaFestivoServicio ) {
        this.diaFestivoServicio = diaFestivoServicio;
    }

    @GetMapping("/esFestivo")
    public String getAll(@RequestParam("ano") int ano,
                             @RequestParam("mes") int mes,
                             @RequestParam("dia") int dia) {
        return diaFestivoServicio.esFestivo(ano, dia, mes);
    }
}
