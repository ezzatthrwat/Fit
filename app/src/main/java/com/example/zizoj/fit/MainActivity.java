package com.example.zizoj.fit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.zizoj.fit.Calories.Calorie_Activity;
import com.example.zizoj.fit.Workout.Workout_Activity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout layout = findViewById(R.id.workoutLayout);
        RelativeLayout layout2 = findViewById(R.id.calLayout);
        RelativeLayout layout3  = findViewById(R.id.neargymbtnLayout);

        Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.animation_btns);
        layout.startAnimation(animAlpha);
        layout2.startAnimation(animAlpha);
        layout3.startAnimation(animAlpha);





    }

    public void WorkoutBtn(View view) {
        animationButton(view);

        Intent intent = new Intent(MainActivity.this, Workout_Activity.class);
        startActivity(intent);
    }

    public void BMRbtn(View view) {
        animationButton(view);

        Intent intent = new Intent(MainActivity.this, Calorie_Activity.class);
        startActivity(intent);
    }

    public void nearGymBtn(View view) {

        startActivity(new Intent(MainActivity.this , GymMapsActivity.class));
    }

    public void animationButton(View view){
        Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        view.startAnimation(animAlpha);
    }


}
