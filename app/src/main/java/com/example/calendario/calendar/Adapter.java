package com.example.calendario.calendar;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendario.Banco_de_dados.SQLite;
import com.example.calendario.MainActivity;
import com.example.calendario.R;
import com.example.calendario.class_arrays_list.datas_descricao;
import com.example.calendario.config.config_inicial;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<meu_ViewHolder> {
    private static ArrayList<datas_descricao> dias = new ArrayList<>();

    private static Context main_context;

    public Adapter(Context main) {
        setMain_context(main);
    }

    public static Context getMain_context() {
        return main_context;
    }

    public static void setMain_context(Context main_context) {
        Adapter.main_context = main_context;
    }

    @NonNull
    @Override
    public meu_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.celula_calendar_recicle_view, parent, false);

        return new meu_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull meu_ViewHolder holder, int position) {
        TextView descricao = holder.getDescricao();
        descricao.setText(dias.get(position).getDescricao());

        TextView texto_calendar = (TextView) holder.getDescricao();
        ViewGroup.LayoutParams params = texto_calendar.getLayoutParams();

        AppCompatImageButton imageButton_apagar_descricao = holder.getBT_apagar_programacao();

        for (int i = 0; i < dias_calendar_padrao.getDatas_padrao().size(); i++) {
            if (dias_calendar_padrao.getDatas_padrao().get(i).getId() == dias.get(position).getId()) {
                imageButton_apagar_descricao.setVisibility(View.INVISIBLE);
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                imageButton_apagar_descricao.setVisibility(View.VISIBLE);
                params.width = 0;
            }
        }
        texto_calendar.requestLayout();

        // Apagar Descrição
        imageButton_apagar_descricao.setOnClickListener((View v) -> {
            if (dias.get(position).getId() != -1) {
                SQLite sqLite = new SQLite(SQLite.getContext_geral());
                int linhas_removidas = SQLite.remover_dados(sqLite, dias.get(position).getId());
                if (linhas_removidas > 0) {
                    SQLite.atualizar_lista_de_dados(sqLite, Adapter.this, dias.get(position).getDia(),
                                                                            dias.get(position).getMes(),
                                                                            dias.get(position).getAno());
                    this.notifyDataSetChanged();
                    new config_inicial(config_inicial.getActivity_principal(), config_inicial.getSqLite_principal(), config_inicial.getAdapter_principal());
                }
            }
        });
        //------------------------------------------------------------------------------------------

        // Atualizar datas

        descricao.setOnClickListener((View v) -> {
            if (dias.get(position).getId() != -1) {

                Activity activity = (Activity) holder.itemView.getContext();
                ConstraintLayout CL_main = activity.findViewById(R.id.main);

                LayoutInflater main_inflater = LayoutInflater.from(holder.itemView.getContext());
                View pop_up = main_inflater.inflate(R.layout.pop_up_atualizar_datas, null);
                View LL_pop_up = pop_up.findViewById(R.id.LL_main_pop_up_atualizar_data);

                // NumberPicker Atualizar

                NumberPicker NP_pop_up_atualizar_dia = pop_up.findViewById(R.id.NP_pop_up_atualizar_dia);
                NP_pop_up_atualizar_dia.setMinValue(1);
                NP_pop_up_atualizar_dia.setMaxValue(31);
                NP_pop_up_atualizar_dia.setValue(obj_calendar.getDia());

                NumberPicker NP_pop_up_atualizar_mes = pop_up.findViewById(R.id.NP_pop_up_atualizar_mes);
                NP_pop_up_atualizar_mes.setMinValue(1);
                NP_pop_up_atualizar_mes.setMaxValue(12);
                NP_pop_up_atualizar_mes.setValue(obj_calendar.getMes());

                NumberPicker NP_pop_up_atualizar_ano = pop_up.findViewById(R.id.NP_pop_up_atualizar_ano);
                NP_pop_up_atualizar_ano.setMinValue(1900);
                NP_pop_up_atualizar_ano.setMaxValue((obj_calendar.getAno() + 10));
                NP_pop_up_atualizar_ano.setValue(obj_calendar.getAno());

                //------------------------------------------------------------------------------------------

                // EditText Atualizar

                EditText TV_pop_up_atualizar_descricao = (EditText) pop_up.findViewById(R.id.TV_pop_up_atualizar_descricao);
                TV_pop_up_atualizar_descricao.setText(dias.get(position).getDescricao());

                //------------------------------------------------------------------------------------------

                LL_pop_up.setId(View.generateViewId());

                CL_main.addView(LL_pop_up);

                LL_pop_up.getLayoutParams().width = 0;
                LL_pop_up.getLayoutParams().height = 0;
                LL_pop_up.setTranslationZ(100f);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(CL_main);

                constraintSet.connect(LL_pop_up.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                constraintSet.connect(LL_pop_up.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
                constraintSet.connect(LL_pop_up.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
                constraintSet.connect(LL_pop_up.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);

                constraintSet.applyTo(CL_main);

                AppCompatButton BT_pop_up_atualizar_fechar = pop_up.findViewById(R.id.BT_pop_up_atualizar_fechar);
                BT_pop_up_atualizar_fechar.setOnClickListener((View v_pop_up_fechar) -> {
                    CL_main.removeView(LL_pop_up);
                });

                AppCompatButton BT_pop_up_atualizar_salvar = pop_up.findViewById(R.id.BT_pop_up_atualizar_salvar);
                BT_pop_up_atualizar_salvar.setOnClickListener((View v_pop_up_salvar) -> {
                    if (!TV_pop_up_atualizar_descricao.getText().toString().trim().isEmpty()) {
                        SQLite.atualizar_dados(new SQLite(holder.itemView.getContext()),
                                                dias.get(position).getId(),
                                                NP_pop_up_atualizar_dia.getValue(),
                                                NP_pop_up_atualizar_mes.getValue(),
                                                NP_pop_up_atualizar_ano.getValue(),
                                                TV_pop_up_atualizar_descricao.getText().toString());

                        Adapter.getDias().clear();
                        SQLite.atualizar_lista_de_dados(new SQLite(holder.itemView.getContext()),
                                                        this,
                                                        NP_pop_up_atualizar_dia.getValue(),
                                                        NP_pop_up_atualizar_mes.getValue(),
                                                        NP_pop_up_atualizar_ano.getValue());

                        CL_main.removeView(LL_pop_up);

                        this.notifyDataSetChanged();
                        new config_inicial(config_inicial.getActivity_principal(), config_inicial.getSqLite_principal(), config_inicial.getAdapter_principal());
                    }
                });

                this.notifyDataSetChanged();
                new config_inicial(config_inicial.getActivity_principal(), config_inicial.getSqLite_principal(), config_inicial.getAdapter_principal());

            }
        });
    }

    @Override
    public int getItemCount() {
        return dias.size();
    }

    public static ArrayList<datas_descricao> getDias() {
        return dias;
    }

    public static void setDias(ArrayList<datas_descricao> dias) {
        Adapter.dias = dias;
    }
}
