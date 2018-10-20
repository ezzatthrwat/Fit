package com.example.zizoj.fit.Calories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zizoj.fit.R;

public class AftrCal_Activity extends AppCompatActivity {


    TextView maintain , tolosehalf , toloseone , togainhalf , togainone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftr_cal_);

        maintain = findViewById(R.id.maintain);
        tolosehalf = findViewById(R.id.tolose0_5);
        toloseone = findViewById(R.id.tolose1);
        togainhalf = findViewById(R.id.togain0_5);
        togainone = findViewById(R.id.togain1);

        double DRI = Double.parseDouble(String.valueOf(getIntent().getDoubleExtra("DRI" , 5)));

        maintain.setText( String.valueOf(DRI));
        tolosehalf.setText( String.valueOf(DRI - 500));
        toloseone.setText( String.valueOf(DRI - 1000));
        togainhalf.setText( String.valueOf(DRI + 500) );
        togainone.setText( String.valueOf(DRI  + 1000) );



    }
}
