package com.franco.dinnerroulette;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class myRoulleteActivity extends AppCompatActivity {
    private List<String> names;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_roullete);

        setTitle("Spin The Roulette!!");
        final PieChart pieChart = findViewById(R.id.pieChart);

        List<PieEntry> entries = new ArrayList<>();

        if (getIntent().hasExtra(MainActivity.RESTAURANTS)){
            names = getIntent().getStringArrayListExtra(MainActivity.RESTAURANTS);
        }

        for (String name : names) {
            entries.add(new PieEntry(1f, name));
        }

        setupPieChart(pieChart, entries);


        Button btnSpin = findViewById(R.id.btnSpin);

        btnSpin.setOnClickListener( v -> {


                btnSpin.setEnabled(false);
                btnSpin.setText( R.string.spinning);

                Random random = new Random();
                int randomNum = random.nextInt((7200 - 3600) + 1) + 3600;

                pieChart.spin(4000, pieChart.getRotationAngle(),randomNum, Easing.EasingOption.EaseInOutCirc);

                final Handler handler = new Handler();
                handler.postDelayed( () -> {
                    btnSpin.setEnabled(true);
                    btnSpin.setText("Spin");
                    Toast.makeText(myRoulleteActivity.this, entries.get(pieChart.getIndexForAngle(270)).getLabel(),Toast.LENGTH_LONG).show();
                }, 4000);

        });
    }

    private void setupPieChart(PieChart pieChart, List<PieEntry> entries) {
        PieDataSet set = new PieDataSet(entries, "");
        set.setDrawValues(false);
        PieData data = new PieData(set);
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        Legend legend = pieChart.getLegend();
        Description description = pieChart.getDescription();
        description.setText("");
        legend.setEnabled(false);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.setDrawCenterText(false);
        pieChart.setData(data);
        pieChart.invalidate();
    }
}
