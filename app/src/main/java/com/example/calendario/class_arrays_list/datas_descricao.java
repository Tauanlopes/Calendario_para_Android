package com.example.calendario.class_arrays_list;

public class datas_descricao {

    private int id = 0;
    private int dia = 0;
    private int mes = 0;
    private int ano = 0;
    private String descricao = "";

    public datas_descricao(int dia, int mes, int ano, String descricao) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.descricao = descricao;
    }

    public datas_descricao(int id, int dia, int mes, int ano, String descricao) {
        this.id = id;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
