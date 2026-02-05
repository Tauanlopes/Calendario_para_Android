package com.example.calendario.config;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.calendario.Banco_de_dados.Database_Implements;
import com.example.calendario.Banco_de_dados.SQLite;
import com.example.calendario.R;
import com.example.calendario.calendar.Adapter;
import com.example.calendario.calendar.obj_calendar;
import com.example.calendario.class_arrays_list.datas_descricao;

public class config_inicial {

    private static Activity activity_principal;
    private static SQLite sqLite_principal;
    private static Adapter adapter_principal;
    public config_inicial(Activity activity, SQLite sqLite, Adapter adapter) {
        activity_principal = activity;
        sqLite_principal = sqLite;
        adapter_principal = adapter;

        int dia = obj_calendar.getDia();
        int mes = obj_calendar.getMes();
        int ano = obj_calendar.getAno();

        SQLite.atualizar_lista_de_dados(sqLite, adapter, dia, mes , ano);

        TextView textView_dia_programacao = (TextView) activity.findViewById(R.id.TV_titulo_dia_recycle_view);
        String str_dia = "";
        if (dia < 10) {
            str_dia = "0" + dia;
        } else {
            str_dia = "" + dia;
        }
        textView_dia_programacao.setText(str_dia);

        if (adapter.getDias().isEmpty()) {
            TextView textView_sem_programacao = (TextView) activity.findViewById(R.id.TV_sem_programacao);
            textView_sem_programacao.setVisibility(TextView.VISIBLE);
        } else {
            TextView textView_sem_programacao = (TextView) activity.findViewById(R.id.TV_sem_programacao);
            textView_sem_programacao.setVisibility(TextView.INVISIBLE);
        }
    }

    public static Activity getActivity_principal() {
        return activity_principal;
    }

    public static SQLite getSqLite_principal() {
        return sqLite_principal;
    }

    public static Adapter getAdapter_principal() {
        return adapter_principal;
    }
}
