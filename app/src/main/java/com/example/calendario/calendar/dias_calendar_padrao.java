package com.example.calendario.calendar;

import com.example.calendario.class_arrays_list.datas_descricao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dias_calendar_padrao {
    private static List<datas_descricao> datas_padrao = new ArrayList<>(Arrays.asList(
            // FERIADOS NACIONAIS 2026
            new datas_descricao(-1, 1, 1, 0, "Confraternização Universal"),
            new datas_descricao(-1, 17, 2, 0, "Carnaval"),
            new datas_descricao(-1, 3, 4, 0, "Sexta-feira Santa"),
            new datas_descricao(-1, 21, 4, 0, "Tiradentes"),
            new datas_descricao(-1, 1, 5, 0, "Dia do Trabalho"),
            new datas_descricao(-1, 4, 6, 0, "Corpus Christi"),
            new datas_descricao(-1, 7, 9, 0, "Independência do Brasil"),
            new datas_descricao(-1, 12, 10, 0, "Nossa Senhora Aparecida"),
            new datas_descricao(-1, 2, 11, 0, "Finados"),
            new datas_descricao(-1, 15, 11, 0, "Proclamação da República"),
            new datas_descricao(-1, 20, 11, 0, "Dia Nacional de Zumbi e da Consciência Negra"),
            new datas_descricao(-1, 25, 12, 0, "Natal"),

            // PRINCIPAIS FERIADOS ESTADUAIS/MUNICIPAIS
            new datas_descricao(-1, 20, 1, 0, "Dia de São Sebastião (Rio de Janeiro)"),
            new datas_descricao(-1, 25, 1, 0, "Aniversário de São Paulo (São Paulo)"),
            new datas_descricao(-1, 23, 4, 0, "Dia de São Jorge (Rio de Janeiro)"),
            new datas_descricao(-1, 9, 7, 0, "Revolução Constitucionalista (São Paulo)"),
            new datas_descricao(-1, 26, 7, 0, "Aniversário de Campo Grande"),
            new datas_descricao(-1, 15, 8, 0, "Assunção de Nossa Senhora (Belo Horizonte)"),
            new datas_descricao(-1, 8, 12, 0, "Nossa Senhora da Conceição (Recife/BH)")
    ));

    public static List<datas_descricao> getDatas_padrao() {
        return datas_padrao;
    }

    public static void setDatas_padrao(List<datas_descricao> datas_padrao) {
        dias_calendar_padrao.datas_padrao = datas_padrao;
    }
}
