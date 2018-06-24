package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Utilities.JsonUtility;
import com.example.android.bakingapp.Utilities.RecipeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.RecipeOnClickListener{
    private static String JsonUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    @BindView(R.id.recycler_view)RecyclerView mRecyclerView;
    @BindView(R.id.butterknife) TextView butterText;
    //private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
       // mRecyclerView =findViewById(R.id.recycler_view);
        Configuration config = getResources().getConfiguration();
        /*
        if(config.smallestScreenWidthDp >=600){
            //set columns to 4
            GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        }else {
            //set columns to 1

        }
        */
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeAdapter=new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        new GetRecipes().execute();
    }
    @Override
    public void onClick(int index) {
        Log.d("recipeAct", index+"");
        Intent intent = new Intent(RecipeActivity.this, RecipeDetail.class);
        intent.putExtra("jsonIndex", index);    //send the index of the recipes clicked to detail activity
        startActivity(intent);
    }

    public class GetRecipes extends AsyncTask<Void, Void, String[]>{
        String[] recipeNames = new String[4];
        @Override
        protected String[] doInBackground(Void... voids) {
            try{
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUrl);
                Log.d("jsonRaw", jsonStringFromWeb);
                recipeNames = JsonUtility.getRecipeJSON(jsonStringFromWeb);
                Log.d("recipeNameGet", recipeNames[1]);
                return recipeNames;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String[] strings) {
            //TODO populate adapter
            //Log.d("RecipeNamePost", strings[1]);
            mRecipeAdapter.updateRecipes(strings);
            super.onPostExecute(strings);
        }
    }
}
