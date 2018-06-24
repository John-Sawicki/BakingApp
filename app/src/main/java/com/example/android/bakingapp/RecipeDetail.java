package com.example.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bakingapp.Utilities.StepAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetail extends AppCompatActivity implements StepAdapter.StepOnClickInterface {
    @BindView(R.id.detail_ingredients)TextView ingredientsText;
    @BindView(R.id.detail_steps)RecyclerView stepsRecyclerView;
    @BindView(R.id.next_button)Button nextButton;
    private StepAdapter mStepAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        Configuration config = getResources().getConfiguration();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mStepAdapter = new StepAdapter(this);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(mStepAdapter);
        new getIngredients().execute();


        Intent detailIntent = new Intent(RecipeDetail.this, RecipeStepDetail.class);
    }

    @Override
    public void onClick(int index) {
        Log.d("recipeDetailClick", index+"");
    }
    public class getIngredients extends AsyncTask<Void, Void, String >{
        String ingredients = new String();

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            ingredientsText.setText(s);
        }
    }
    public class getSteps extends AsyncTask<Integer, Void, String[]>{
        String[] steps = new String[20];    //there will be less than 20 steps

        @Override
        protected String[] doInBackground(Integer... integers) {
            return null;
        }

        @Override
        protected void onPostExecute(String[] s) {
            mStepAdapter.updateSteps(s);
        }
    }
}
