package com.festivos.ec.decirdiasfestivos.utils;

import com.festivos.ec.decirdiasfestivos.dominio.entidades.dto.ResponseCalculate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
public class HolidayUtil {
    private int year;
    private int easterMonth;
    private int easterDay;
    private List<ResponseCalculate> holidays;

    public HolidayUtil(int year) {
        this.year = year;
        this.holidays = new ArrayList<>();
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int g = (8 * b + 13) / 25;
        int h = (19 * a + b - d - g + 15) % 30;
        int j = c / 4;
        int k = c % 4;
        int m = (a + 11 * h) / 319;
        int r = (2 * e + 2 * j - k - h + m + 32) % 7;
        this.easterMonth = (h - m + r + 90) / 25;
        this.easterDay = (h - m + r + this.easterMonth + 19) % 32;
        this.easterMonth--;
        this.holidays.add(new ResponseCalculate("0","1"));
        this.holidays.add(new ResponseCalculate("4","1"));
        this.holidays.add(new ResponseCalculate("6", "20"));
        this.holidays.add(new ResponseCalculate("7","7"));
        this.holidays.add(new ResponseCalculate("11","8"));
        this.holidays.add(new ResponseCalculate("11","25"));
        this.calculateEmiliani(0, 6);
        this.calculateEmiliani(2, 19);
        this.calculateEmiliani(5, 29);
        this.calculateEmiliani(7, 15);
        this.calculateEmiliani(9, 12);
        this.calculateEmiliani(10, 1);
        this.calculateEmiliani(10, 11);
        this.calculateOtherHoliday(-3, false);
        this.calculateOtherHoliday(-2, false);
        this.calculateOtherHoliday(40, true);
        this.calculateOtherHoliday(60, true);
        this.calculateOtherHoliday(68, true);
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
}