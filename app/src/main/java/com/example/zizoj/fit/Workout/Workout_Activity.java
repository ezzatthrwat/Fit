package com.example.zizoj.fit.Workout;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.zizoj.fit.R;
import com.example.zizoj.fit.Utils.ImageWorkoutAdapter;

public class Workout_Activity extends AppCompatActivity {

    ViewPager viewPager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_);


        viewPager = findViewById(R.id.slideshow);
        ImageWorkoutAdapter imageWorkoutAdapter = new ImageWorkoutAdapter(this);
        viewPager.setAdapter(imageWorkoutAdapter);



        }











}
