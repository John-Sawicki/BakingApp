package com.example.android.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.bakingapp.Utilities.JsonUtility;
import com.example.android.bakingapp.Utilities.RecipeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.RecipeOnClickListener{
    private static String JsonUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    @BindView(R.id.recycler_view)RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter=new RecipeAdapter(this);
        Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp >=600){
            //set columns to 4
        }else {
            //set columns to 1
        }
        ButterKnife.bind(this);

    }

    @Override
    public void onClick(String recipe) {
            Context context = this;
        Toast.makeText(context,recipe, Toast.LENGTH_SHORT).show();

    }

    public class GetRecipes extends AsyncTask<Void, Void, String[]>{
        String[] recipeNames = new String[13];
        @Override
        protected String[] doInBackground(Void... voids) {
            try{
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUrl);
                recipeNames = JsonUtility.getRecipeJSON(jsonStringFromWeb);
                return recipeNames;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String[] strings) {
            //TODO populate adapter
            super.onPostExecute(strings);
        }
    }
}
