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

import com.example.android.bakingapp.Utilities.JsonUtility;
import com.example.android.bakingapp.Utilities.StepAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetail extends AppCompatActivity implements StepAdapter.StepOnClickInterface {
    @BindView(R.id.detail_ingredients)TextView ingredientsText;
    @BindView(R.id.step_recycler_view)RecyclerView stepsRecyclerView;
    private StepAdapter mStepAdapter;
    private int recipeNumber;
    String[] stepDummy = {"1","2","3","4","5","1","2","3","4","5","1","2","3","4","5","1","2","3","4","5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        recipeNumber = getIntent().getIntExtra("recipeIndex", 0);
        Configuration config = getResources().getConfiguration();//use to set up video and instructions
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mStepAdapter = new StepAdapter(this);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(mStepAdapter);
        //mStepAdapter.updateSteps(stepDummy);
        // test dummy data before json
        Log.d("recipeNumber",recipeNumber+"");
        new getSteps().execute(recipeNumber);//only get the steps for the selected recipe from RecipeActivity
        new getIngredients().execute(recipeNumber);
        Intent detailIntent = new Intent(RecipeDetail.this, RecipeStepDetail.class);
    }

    @Override
    public void onClick(int index) {
        Log.d("recipeDetailClick", index+"");
        Intent intent = new Intent(RecipeDetail.this, RecipeStepDetail.class);
        intent.putExtra("recipeIndex",recipeNumber);
        intent.putExtra("stepIndex", index);    //step number for long description and movie
        startActivity(intent);
    }
    public class getIngredients extends AsyncTask<Integer, Void, String >{
        String ingredients = new String();

        @Override
        protected String doInBackground(Integer... integers) {
            try{
                int recipeInt = integers[0];
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUtility.JsonUrl);
                ingredients = JsonUtility.getIngrediants(jsonStringFromWeb, recipeInt);
                return ingredients;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
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
            try{
                int recipeInt = integers[0];
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUtility.JsonUrl);
                steps = JsonUtility.getStepsShort(jsonStringFromWeb,recipeInt);
                return steps;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String[] s) {
            mStepAdapter.updateSteps(s);
        }
    }
}
