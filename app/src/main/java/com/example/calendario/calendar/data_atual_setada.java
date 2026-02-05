package com.example.calendario.calendar;

import java.util.Calendar;

public class data_atual_setada {

    private static final String[] meses = new String[]{
            "janeiro",
            "fevereiro",
            "março",
            "abril",
            "maio",
            "junho",
            "julho",
            "agosto",
            "setembro",
            "outubro",
            "novembro",
            "dezembro"
    };
    private Calendar calendar = null;

    public data_atual_setada() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
    }

    public static String getMes(int position) {
        return meses[position];
    }

    public Calendar getCalendar() {
        if (calendar != null) {
            return calendar;
        } else {
            calendar = Calendar.getInstance();
            return calendar;
        }
    }
}
