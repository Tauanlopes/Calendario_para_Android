package com.example.calendario;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendario.Banco_de_dados.SQLite;
import com.example.calendario.calendar.Adapter;
import com.example.calendario.calendar.obj_calendar;
import com.example.calendario.config.config_inicial;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static Boolean BT_add_datas = false;

    SQLite sqLite;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sqLite = new SQLite(MainActivity.this);
        Adapter adapter = new Adapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.calendario_recicle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.getDias().clear();

        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = (calendar.get(Calendar.MONTH) + 1);
        int ano = calendar.get(Calendar.YEAR);

        new config_inicial(this, sqLite, adapter);


        // Pegar datas clicadas no CalendarView
        CalendarView calendarView = findViewById(R.id.calendario_view);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            obj_calendar.setDia(dayOfMonth);
            obj_calendar.setMes((month + 1));
            obj_calendar.setAno(year);

            Adapter.getDias().clear();

            TextView textView_dia_programacao = (TextView) findViewById(R.id.TV_titulo_dia_recycle_view);
            String str_dia = "";
            if (dayOfMonth < 10) {
                str_dia = "0" + dayOfMonth;
            } else {
                str_dia = "" + dayOfMonth;
            }
            textView_dia_programacao.setText(str_dia);

            SQLite.atualizar_lista_de_dados(sqLite, adapter, dayOfMonth, (month + 1), year);

            if (Adapter.getDias().isEmpty()) {
                TextView textView_sem_programacao = (TextView) findViewById(R.id.TV_sem_programacao);
                textView_sem_programacao.setVisibility(TextView.VISIBLE);
            } else {
                TextView textView_sem_programacao = (TextView) findViewById(R.id.TV_sem_programacao);
                textView_sem_programacao.setVisibility(TextView.INVISIBLE);
            }

            adapter.notifyDataSetChanged();
        });
        //------------------------------------------------------------------------------------------

        // Adicionar datas
        AppCompatButton BT_add_data = (AppCompatButton) findViewById(R.id.BT_add_data);
        BT_add_data.setOnClickListener(v -> {
            if (BT_add_datas == false) {
                BT_add_data.setEnabled(false);
                ConstraintLayout constraintLayout = findViewById(R.id.main);

                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View cl_pop_up_add_datas = layoutInflater.inflate(R.layout.pop_up_add_datas, null, false);
                View LL_pop_up_add = cl_pop_up_add_datas.findViewById(R.id.pop_up_add_datas);

                NumberPicker NP_dia = (NumberPicker) cl_pop_up_add_datas.findViewById(R.id.NP_pop_up_dia);
                NP_dia.setMinValue(1);
                NP_dia.setMaxValue(31);
                NP_dia.setValue(obj_calendar.getDia());

                NumberPicker NP_mes = (NumberPicker) cl_pop_up_add_datas.findViewById(R.id.NP_pop_up_mes);
                NP_mes.setMinValue(1);
                NP_mes.setMaxValue(12);
                NP_mes.setValue(obj_calendar.getMes());

                NumberPicker NP_ano = (NumberPicker) cl_pop_up_add_datas.findViewById(R.id.NP_pop_up_ano);
                NP_ano.setMinValue(1900);
                NP_ano.setMaxValue((obj_calendar.getAno() + 10));
                NP_ano.setValue(obj_calendar.getAno());

                EditText TV_pop_up_descricao = (EditText) cl_pop_up_add_datas.findViewById(R.id.TV_pop_up_descricao);

                LL_pop_up_add.setId(View.generateViewId());

                constraintLayout.addView(LL_pop_up_add);

                LL_pop_up_add.getLayoutParams().width = 0;
                LL_pop_up_add.getLayoutParams().height = 0;
                LL_pop_up_add.setTranslationZ(100f);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(LL_pop_up_add.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                constraintSet.connect(LL_pop_up_add.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
                constraintSet.connect(LL_pop_up_add.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
                constraintSet.connect(LL_pop_up_add.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);

                constraintSet.applyTo(constraintLayout);

                BT_add_datas = true;

                AppCompatButton BT_pop_up_fechar = (AppCompatButton) cl_pop_up_add_datas.findViewById(R.id.BT_pop_up_fechar);
                BT_pop_up_fechar.setOnClickListener((View bt_v) -> {
                    constraintLayout.removeView(LL_pop_up_add);
                    BT_add_datas = false;
                    BT_add_data.setEnabled(true);
                });

                AppCompatButton BT_pop_up_salvar = (AppCompatButton) cl_pop_up_add_datas.findViewById(R.id.BT_pop_up_salvar);
                BT_pop_up_salvar.setOnClickListener((View bt_v) -> {

                    String descricao = TV_pop_up_descricao.getText().toString();

                    if (!descricao.trim().isEmpty()) {
                        long linhas_afetadas = sqLite.colocar_dados(sqLite,
                                NP_dia.getValue(),
                                NP_mes.getValue(),
                                NP_ano.getValue(),
                                descricao);

                        if (linhas_afetadas > 0) {
                            constraintLayout.removeView(LL_pop_up_add);
                            BT_add_datas = false;
                            BT_add_data.setEnabled(true);
                            new config_inicial(this, sqLite, adapter);
                        }
                    } else {

                    }

                    SQLite.atualizar_lista_de_dados(sqLite, adapter, obj_calendar.getDia(), obj_calendar.getMes(), obj_calendar.getAno());
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        sqLite.close();
        super.onDestroy();
    }
}