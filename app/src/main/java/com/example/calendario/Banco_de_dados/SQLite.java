package com.example.calendario.Banco_de_dados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.calendario.calendar.Adapter;
import com.example.calendario.calendar.dias_calendar_padrao;
import com.example.calendario.class_arrays_list.datas_descricao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLite extends SQLiteOpenHelper implements Database_Implements {

    private static Context context_geral;

    public SQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context_geral = context;
    }

    public static Context getContext_geral() {
        return context_geral;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETAR_TABELA);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long colocar_dados(SQLite sqLite, int dia, int mes, int ano, String descricao) {
        SQLiteDatabase db = sqLite.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Database_Implements.COLUNA_DIA, dia);
        contentValues.put(Database_Implements.COLUNA_MES, mes);
        contentValues.put(Database_Implements.COLUNA_ANO, ano);
        contentValues.put(Database_Implements.COLUNA_DESCRICAO, descricao);

        long linhas_afetadas = db.insert(Database_Implements.DATABASE_TABLE_NAME, null, contentValues);
        return linhas_afetadas;
    }

    public Cursor pegar_dados(SQLite sqLite, int dia, int mes, int ano) {
        SQLiteDatabase db = sqLite.getReadableDatabase();

        String[] projecao = {
                Database_Implements.COLUNA_ID,
                Database_Implements.COLUNA_DIA,
                Database_Implements.COLUNA_MES,
                Database_Implements.COLUNA_ANO,
                Database_Implements.COLUNA_DESCRICAO
        };

        String selecao = " ";

        String[] selecaoArgumentos = {dia + "", mes + "", ano + ""};
        List<String> arraySelecaoArgumentos = new ArrayList<>(Arrays.asList(selecaoArgumentos));

        if (dia > 0) {
            selecao += Database_Implements.COLUNA_DIA + " = ?";
        }
        if (mes > 0) {
            if (dia > 0) {
                selecao += " AND ";
            }
            selecao += Database_Implements.COLUNA_MES + " = ?";
        }
        if (ano > 0) {
            if (dia > 0 || mes > 0) {
                selecao += " AND ";
            }
            selecao += Database_Implements.COLUNA_ANO + " = ?";
        }
        if (dia > 0 && mes > 0 && ano > 0) {
            selecao = Database_Implements.COLUNA_DIA + " = ? AND " +
                    Database_Implements.COLUNA_MES + " = ? AND " +
                    Database_Implements.COLUNA_ANO + " = ?";
        }


        if (dia > 0 && mes > 0 && ano > 0){

        } else if (dia > 0 && mes > 0) {
            arraySelecaoArgumentos.remove(2);
        } else if (mes > 0 && ano > 0) {
            arraySelecaoArgumentos.remove(0);
        } else if (dia > 0 && ano > 0) {
            arraySelecaoArgumentos.remove(1);
        } else if (dia > 0) {
            arraySelecaoArgumentos.remove(1);
            arraySelecaoArgumentos.remove(1);
        } else if (mes > 0) {
            arraySelecaoArgumentos.remove(0);
            arraySelecaoArgumentos.remove(1);
        } else if (ano > 0) {
            arraySelecaoArgumentos.remove(0);
            arraySelecaoArgumentos.remove(0);
        }

        String[] novoSelecaoArgumentos = arraySelecaoArgumentos.toArray(new String[0]);

        String sortOrdem = Database_Implements.COLUNA_DESCRICAO + " DESC";

        Cursor cursor;

        cursor = db.query(
                Database_Implements.DATABASE_TABLE_NAME,
                projecao,
                selecao,
                novoSelecaoArgumentos,
                null,
                null,
                sortOrdem
        );

        return cursor;
    }

    public static int remover_dados(SQLite sqLite, int id) {
        SQLiteDatabase db = sqLite.getWritableDatabase();

        String selecao = Database_Implements.COLUNA_ID + " LIKE ?";

        String[] selecaoArgs = { id + "" };

        return db.delete(Database_Implements.DATABASE_TABLE_NAME,
                selecao, selecaoArgs);
    }

    public static int atualizar_dados(SQLite sqLite, int id, int dia, int mes, int ano, String descricao) {
        SQLiteDatabase db = sqLite.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Database_Implements.COLUNA_DIA, dia);
        contentValues.put(Database_Implements.COLUNA_MES, mes);
        contentValues.put(Database_Implements.COLUNA_ANO, ano);
        contentValues.put(Database_Implements.COLUNA_DESCRICAO, descricao);

        String selecao = Database_Implements.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = { id + "" };

        return db.update(Database_Implements.DATABASE_TABLE_NAME,
                contentValues,
                selecao,
                selecaoArgs);
    }

    public static void atualizar_lista_de_dados(SQLite sqLite, Adapter adapter, int dayOfMonth, int month, int year) {
        Adapter.getDias().clear();

        for (int i = 0 ; i < dias_calendar_padrao.getDatas_padrao().size(); i++) {
            if(dayOfMonth == dias_calendar_padrao.getDatas_padrao().get(i).getDia() &&
                    month == dias_calendar_padrao.getDatas_padrao().get(i).getMes()) {
                Adapter.getDias().add(dias_calendar_padrao.getDatas_padrao().get(i));
            }
        }

        Cursor cursor = sqLite.pegar_dados(sqLite, dayOfMonth, month, year);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow((Database_Implements.COLUNA_ID)));
            int d = cursor.getInt(cursor.getColumnIndexOrThrow((Database_Implements.COLUNA_DIA)));
            int m = cursor.getInt(cursor.getColumnIndexOrThrow((Database_Implements.COLUNA_MES)));
            int a = cursor.getInt(cursor.getColumnIndexOrThrow((Database_Implements.COLUNA_ANO)));
            String des = cursor.getString(cursor.getColumnIndexOrThrow(Database_Implements.COLUNA_DESCRICAO));

            Adapter.getDias().add(new datas_descricao(id, d, m, a, des));
        }

        cursor.close();
    }
}
