package com.example.android.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bakingapp.Utilities.JsonUtility;
import com.example.android.bakingapp.Utilities.StepAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.Provider.IngredContract.IngredEntry;

import static com.example.android.bakingapp.Provider.IngredContract.IngredEntry.CONTENT_URI;
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
        recipeNumber = getIntent().getIntExtra("recipeIndex", 0);   //pass this value to RecipeStepDetail
        //Configuration config = getResources().getConfiguration();//use to set up video and instructions
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
        //Intent detailIntent = new Intent(RecipeDetail.this, RecipeStepDetail.class);
    }

    @Override
    public void onClick(int index) {
        Intent intent = new Intent(RecipeDetail.this, RecipeStepDetail.class);
        Log.d("recipeNumberDetail", recipeNumber+"");
        Log.d("recipeDetailClick", index+"");
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
                ingredients = JsonUtility.getIngredients(jsonStringFromWeb, recipeInt);
                return ingredients;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            ingredientsText.setText(s);
            ContentValues contentValues = new ContentValues();
            contentValues.put(IngredEntry.COLUMN_INGREDIENTS, s);//used to store the value for the widget
            //Uri INGRED_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGRED).build();
            //getContentResolver().update(INGRED_URI, contentValues, null, null);
            getContentResolver().insert(CONTENT_URI, contentValues);
            Log.d("RecipeDetail post stg", s);


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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("saveRecipe", recipeNumber);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipeNumber =savedInstanceState.getInt("saveRecipe");
    }
}
