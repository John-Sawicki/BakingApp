package com.example.android.bakingapp;


import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetail extends AppCompatActivity {
    @BindView(R.id.step_movie)VideoView stepVideo;
    @BindView(R.id.no_video_image)ImageView noVideoImage;
    @BindView(R.id.instruction_text)TextView instructionText;
    @BindView(R.id.next_button) Button nextButton;
    int recipeIndex; //ex brownies, this stays the same in this activity
    int stepIndex;  //ex step 2; increment to go to the next step by replacing a fragment
    StepFragment stepFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        recipeIndex = getIntent().getIntExtra("recipeIndex",1);
        Log.d("recipeNumber",recipeIndex+"");
        stepIndex = getIntent().getIntExtra("stepIndex",1);
        Log.d("stepNumber",stepIndex+"");
        StepFragment stepFragment = new StepFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.movie_step_fragment, stepFragment).commit();



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
