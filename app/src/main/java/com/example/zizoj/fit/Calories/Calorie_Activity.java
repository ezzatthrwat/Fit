package com.example.zizoj.fit.Calories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zizoj.fit.R;

public class Calorie_Activity extends AppCompatActivity {

    Spinner exerciseSpinner ;

    EditText agetxt , weighttxt , heighttxt ;

    TextView maintain , tolosehalf , toloseone , togainhalf , togainone ;

    double BMR, exercise , DRI;

    LinearLayout linearLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_);

        intialspinner();

        linearLayout = findViewById(R.id.linearlayputTextcalories);

        agetxt = findViewById(R.id.AgeEditetxt);
        weighttxt = findViewById(R.id.WeightEditetxt);
        heighttxt = findViewById(R.id.heightEditetxt);


        maintain = findViewById(R.id.maintain);
        tolosehalf = findViewById(R.id.tolose0_5);
        toloseone = findViewById(R.id.tolose1);
        togainhalf = findViewById(R.id.togain0_5);
        togainone = findViewById(R.id.togain1);





    }

    public void intialspinner(){

        exerciseSpinner = findViewById(R.id.exerciseSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercise, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter);

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String exerciseType = (String) adapterView.getSelectedItem();

                if(exerciseType.equals("BMR only")){
                    exercise = 1 ;
                }else if (exerciseType.equals("Little to no")){
                    exercise = 1.2 ;
                }else if ((exerciseType.equals("Light exercise(1–3 days per week)"))){
                    exercise = 1.375 ;
                }else if ((exerciseType.equals("Moderate exercise (3–5 days per week)"))){
                    exercise = 1.55 ;
                }else if ((exerciseType.equals("Heavy exercise (6–7 days per week)"))){
                    exercise = 1.725 ;
                }else if ((exerciseType.equals("Very heavy exercise (twice per day, extra heavy workouts)"))){
                    exercise = 1.9 ;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void calcolateBtn(View view) {

        Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        view.startAnimation(animAlpha);


        if (TextUtils.isEmpty(agetxt.getText())){
            agetxt.setError("age is required");

        }else if (TextUtils.isEmpty(weighttxt.getText())){
            weighttxt.setError("weight is required");

        }else if (TextUtils.isEmpty(heighttxt.getText())){
            heighttxt.setError(" height is required");

        }else{
            BMR = (double) ((10 * Integer.parseInt(String.valueOf(weighttxt.getText()))) +
                    (6.25 * Integer.parseInt(String.valueOf(heighttxt.getText())))) -5 * Integer.parseInt(String.valueOf(agetxt.getText())) + 5;

            DRI = BMR * exercise ;

            Intent intent = new Intent(Calorie_Activity.this,AftrCal_Activity.class);
            intent.putExtra("DRI" , DRI);
            startActivity(intent);




        }



    }
}
