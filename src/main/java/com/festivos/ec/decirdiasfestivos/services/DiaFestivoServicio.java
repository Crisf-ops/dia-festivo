package com.festivos.ec.decirdiasfestivos.services;

import com.festivos.ec.decirdiasfestivos.core.repositorioInterfaces.FestivoRepositorio;
import com.festivos.ec.decirdiasfestivos.core.repositorioInterfaces.TipoRepositorio;
import com.festivos.ec.decirdiasfestivos.core.servicesInterfaces.IDiaFestivo;
import com.festivos.ec.decirdiasfestivos.dominio.entidades.Festivos;
import com.festivos.ec.decirdiasfestivos.dominio.entidades.dto.ResponseCalculate;
import com.festivos.ec.decirdiasfestivos.utils.HolidayUtil;
import com.festivos.ec.decirdiasfestivos.utils.TriPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;


@Service
public class DiaFestivoServicio implements IDiaFestivo {

    TipoRepositorio tipoRepositorio;
    FestivoRepositorio festivoRepositorio;
    private static final String MENSAJE_DIA_FESTIVO_NO_ENCONTRADO = "Si se pudo, es dia festivo";

    @Autowired
    public DiaFestivoServicio ( TipoRepositorio tipoRepositorio, FestivoRepositorio festivoRepositorio) {
        this.tipoRepositorio = tipoRepositorio;
        this.festivoRepositorio = festivoRepositorio;
    }

    @Override
    public String esFestivo(int ano, int dia, int mes) {

        Festivos responseFes = festivoRepositorio.findAll().stream()
                .filter(festivos -> validateMesDia.test(dia, mes, festivos))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(responseFes)) return MENSAJE_DIA_FESTIVO_NO_ENCONTRADO;
        if (obtenerFestivoMovido(ano, dia, mes)) return MENSAJE_DIA_FESTIVO_NO_ENCONTRADO;


        return "NO se pudo";
    }

    private boolean obtenerFestivoMovido(int ano, int dia, int mes) {
        HolidayUtil holidayUtil = new HolidayUtil(ano);
        List<ResponseCalculate> list = holidayUtil.getHolidays().stream()
                .filter(festivos -> validateFestivoMovido.test(dia, mes, festivos))
                .toList();

        return !CollectionUtils.isEmpty(list);
    }

    private final TriPredicate<Integer, Integer, Festivos> validateMesDia = ( Integer dia, Integer mes, Festivos festivos) ->
            festivos.getDia().equals(dia) && festivos.getMes().equals(mes);

    private final TriPredicate<Integer, Integer, ResponseCalculate> validateFestivoMovido = (Integer dia, Integer mes, ResponseCalculate festivos) ->
            Integer.valueOf(festivos.getDia()).equals(dia) && Integer.valueOf(festivos.getMes()).equals(mes - 1);

}
