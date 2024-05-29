package com.festivos.ec.decirdiasfestivos.utils;

import com.festivos.ec.decirdiasfestivos.entidades.dto.ResponseCalculate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class HolidayUtil {
    int year;
    int easterMonth;
    int easterDay;
    List<ResponseCalculate> holidays;

    private void calculateEaster() {
        int goldenNumber = getGoldenNumber(year);
        int century = getCentury(year);
        int skippedLeapYears = getSkippedLeapYears(century);
        int correctionFactor = getCorrectionFactor(century);
        int solarCorrection = getSolarCorrection(goldenNumber, century, skippedLeapYears, correctionFactor);
        int lunarCorrection = getLunarCorrection(year);
        int epact = getEpact(goldenNumber, solarCorrection);
        int weekdayCorrection = getWeekdayCorrection(year, lunarCorrection, epact);
        this.easterMonth = getEasterMonth(epact, weekdayCorrection) - 1;  // Ajustar para índice de mes basado en 0
        this.easterDay = getEasterDay(epact, weekdayCorrection, easterMonth);
    }

    private int getGoldenNumber(int year) { return year % 19; }
    private int getCentury(int year) { return year / 100; }
    private int getSkippedLeapYears(int century) { return century / 4; }
    private int getCorrectionFactor(int century) { return (8 * century + 13) / 25; }
    private int getLunarCorrection(int year) { return (year % 100) / 4; }
    private int getEasterMonth(int epact, int weekdayCorrection) { return (epact + weekdayCorrection + 90) / 25; }

    private int getSolarCorrection(int goldenNumber, int century, int skippedLeapYears, int correctionFactor) {
        return (19 * goldenNumber + century - skippedLeapYears - correctionFactor + 15) % 30;
    }

    private int getEpact(int goldenNumber, int solarCorrection) {
        return solarCorrection - ((goldenNumber + 11 * solarCorrection) / 319);
    }

    private int getWeekdayCorrection(int year, int lunarCorrection, int epact) {
        int yearMod100 = year % 100;
        return (2 * (yearMod100 % 4) + 2 * lunarCorrection - (yearMod100 % 4) - epact + 32) % 7;
    }

    private int getEasterDay(int epact, int weekdayCorrection, int easterMonth) {
        return (epact + weekdayCorrection + easterMonth + 19) % 32;
    }

    private void addFixedHolidays() {
        holidays.add(new ResponseCalculate("1", "1"));  // Año Nuevo
        holidays.add(new ResponseCalculate("5", "1"));  // Día del Trabajo
        holidays.add(new ResponseCalculate("7", "20")); // Día de la Independencia
        holidays.add(new ResponseCalculate("8", "7"));  // Batalla de Boyacá
        holidays.add(new ResponseCalculate("12", "8")); // Inmaculada Concepción
        holidays.add(new ResponseCalculate("12", "25")); // Navidad
    }

    private void addEmilianiHolidays() {
        calculateEmiliani(0, 6);  // Día de los Reyes Magos
        calculateEmiliani(2, 19); // San José
        calculateEmiliani(5, 29); // San Pedro y San Pablo
        calculateEmiliani(7, 15); // Asunción de la Virgen
        calculateEmiliani(9, 12); // Día de la Raza
        calculateEmiliani(11, 1); // Todos los Santos
        calculateEmiliani(11, 11); // Independencia de Cartagena
    }

    private void addVariableHolidays() {
        calculateOtherHoliday(-3, false); // Jueves Santo
        calculateOtherHoliday(-2, false); // Viernes Santo
        calculateOtherHoliday(40, true);  // Ascensión del Señor
        calculateOtherHoliday(60, true);  // Corpus Christi
        calculateOtherHoliday(68, true);  // Sagrado Corazón
    }

    private void calculateEmiliani(int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(this.year, month, day);
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                date.add(Calendar.DATE, 1);
                break;
            case 3:
                date.add(Calendar.DATE, 6);
                break;
            case 4:
                date.add(Calendar.DATE, 5);
                break;
            case 5:
                date.add(Calendar.DATE, 4);
                break;
            case 6:
                date.add(Calendar.DATE, 3);
                break;
            case 7:
                date.add(Calendar.DATE, 2);
                break;
            default:
                break;
        }
        this.holidays.add(new ResponseCalculate(String.valueOf(date.get(Calendar.MONTH)) , String.valueOf( date.get(Calendar.DATE))));
    }

    private void calculateOtherHoliday(int days, boolean emiliani) {
        Calendar date = Calendar.getInstance();
        date.set(this.year, this.easterMonth, this.easterDay);
        date.add(Calendar.DATE, days);
        if (emiliani) {
            this.calculateEmiliani(date.get(Calendar.MONTH), date.get(Calendar.DATE));
        } else {
            this.holidays.add(new ResponseCalculate(String.valueOf(date.get(Calendar.MONTH)) , String.valueOf( date.get(Calendar.DATE))));
        }
    }

    public List<ResponseCalculate> getHolidays(int year) {
        this.year = year;
        this.holidays = new ArrayList<>();
        calculateEaster();
        addFixedHolidays();
        addEmilianiHolidays();
        addVariableHolidays();
        return this.holidays;
    }
}