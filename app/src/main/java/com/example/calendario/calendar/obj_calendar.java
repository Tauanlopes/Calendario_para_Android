package com.example.calendario.calendar;

import java.util.Calendar;

public class obj_calendar {
    private static Calendar calendar = Calendar.getInstance();
    private static int dia = calendar.get(Calendar.DAY_OF_MONTH);
    private static int mes = (calendar.get(Calendar.MONTH) + 1);
    private static int ano = calendar.get(Calendar.YEAR);

    public static Calendar getCalendar() {
        return calendar;
    }

    public static void setCalendar(Calendar calendar) {
        obj_calendar.calendar = calendar;
    }

    public static int getDia() {
        return dia;
    }

    public static void setDia(int dia) {
        obj_calendar.dia = dia;
    }

    public static int getMes() {
        return mes;
    }

    public static void setMes(int mes) {
        obj_calendar.mes = mes;
    }

    public static int getAno() {
        return ano;
    }

    public static void setAno(int ano) {
        obj_calendar.ano = ano;
    }
}
