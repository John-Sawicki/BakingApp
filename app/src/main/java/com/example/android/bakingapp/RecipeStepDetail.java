package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
    }
}
