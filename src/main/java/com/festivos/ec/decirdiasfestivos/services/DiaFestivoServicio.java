package com.festivos.ec.decirdiasfestivos.services;

import com.festivos.ec.decirdiasfestivos.core.repositorioInterfaces.FestivoRepositorio;
import com.festivos.ec.decirdiasfestivos.core.repositorioInterfaces.TipoRepositorio;
import com.festivos.ec.decirdiasfestivos.core.servicesInterfaces.IDiaFestivo;
import com.festivos.ec.decirdiasfestivos.entidades.Festivos;
import com.festivos.ec.decirdiasfestivos.entidades.dto.HolidayDTO;
import com.festivos.ec.decirdiasfestivos.entidades.dto.ResponseCalculate;
import com.festivos.ec.decirdiasfestivos.utils.HolidayUtil;
import com.festivos.ec.decirdiasfestivos.utils.TriPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


@Service
public class DiaFestivoServicio implements IDiaFestivo {

    TipoRepositorio tipoRepositorio;
    FestivoRepositorio festivoRepositorio;
    HolidayUtil holidayUtil;
    private static final String MENSAJE_DIA_FESTIVO_NO_ENCONTRADO = "Si se pudo, es dia festivo";

    @Autowired
    public DiaFestivoServicio ( TipoRepositorio tipoRepositorio, FestivoRepositorio festivoRepositorio, HolidayUtil holidayUtil) {
        this.tipoRepositorio = tipoRepositorio;
        this.festivoRepositorio = festivoRepositorio;
        this.holidayUtil = holidayUtil;
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

    @Override
    public List<HolidayDTO> getHolidaysYear(int year) {
        return holidayUtil.getHolidays(year).stream()
                .flatMap(responseCalculate -> festivoRepositorio.findAll().stream()
                        .filter(festivos -> validateDayFullYear.test(festivos, responseCalculate))
                        .map(festivos -> new HolidayDTO(responseCalculate.getDia(), responseCalculate.getMes(), festivos.getNombre())))
                .toList();
    }

    private boolean obtenerFestivoMovido(int ano, int dia, int mes) {
        List<ResponseCalculate> list = holidayUtil.getHolidays(ano).stream()
                .filter(festivos -> validateFestivoMovido.test(dia, mes, festivos))
                .toList();

        return !CollectionUtils.isEmpty(list);
    }

    private final BiPredicate<Festivos, ResponseCalculate> validateDayFullYear = ( Festivos f, ResponseCalculate rc ) ->
            f.getDia().toString().equals(rc.getDia()) && f.getMes().toString().equals(rc.getMes());
    private final TriPredicate<Integer, Integer, Festivos> validateMesDia = ( Integer dia, Integer mes, Festivos festivos) ->
            festivos.getDia().equals(dia) && festivos.getMes().equals(mes);

    private final TriPredicate<Integer, Integer, ResponseCalculate> validateFestivoMovido = (Integer dia, Integer mes, ResponseCalculate festivos) ->
            Integer.valueOf(festivos.getDia()).equals(dia) && Integer.valueOf(festivos.getMes()).equals(mes - 1);

}
