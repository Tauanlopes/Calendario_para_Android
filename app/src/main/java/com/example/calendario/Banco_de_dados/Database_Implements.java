package com.example.calendario.Banco_de_dados;

public interface Database_Implements {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CALENDARIO.DB";
    public static final String DATABASE_TABLE_NAME = "DATABASE_TABLE";
    public static final String COLUNA_ID = "ID";
    public static final String COLUNA_DIA = "DIA";
    public static final String COLUNA_MES = "MES";
    public static final String COLUNA_ANO = "ANO";
    public static final String COLUNA_DESCRICAO = "DESCRICAO";

    public static final String CRIAR_DATABASE =
            "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
                    COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_DIA + " INTEGER," +
                    COLUNA_MES + " INTEGER," +
                    COLUNA_ANO + " INTEGER," +
                    COLUNA_DESCRICAO + " TEXT )";

    public static final String DELETAR_TABELA =
            "DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME;

}
