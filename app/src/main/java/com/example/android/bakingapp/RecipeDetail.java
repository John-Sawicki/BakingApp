package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetail extends AppCompatActivity {
    @BindView(R.id.detail_ingredients)TextView ingredientsText;
    @BindView(R.id.detail_steps)RecyclerView stepsRecyclerView;
    @BindView(R.id.next_button)Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
    }
}
