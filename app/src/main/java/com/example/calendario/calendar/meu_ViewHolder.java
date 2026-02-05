package com.example.calendario.calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendario.R;

public class meu_ViewHolder extends RecyclerView.ViewHolder {
    private TextView dia;
    private TextView descricao;
    private AppCompatImageButton BT_apagar_programacao;

    public meu_ViewHolder(@NonNull View itemView) {
        super(itemView);

        descricao = (TextView) itemView.findViewById(R.id.texto_calendar);
        BT_apagar_programacao = (AppCompatImageButton) itemView.findViewById(R.id.BT_apagar_programacao);
    }

    public TextView getDia() {
        return dia;
    }

    public TextView getDescricao() {
        return descricao;
    }

    public AppCompatImageButton getBT_apagar_programacao() {
        return BT_apagar_programacao;
    }
}
