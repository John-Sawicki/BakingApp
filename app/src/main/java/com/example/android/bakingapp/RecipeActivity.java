package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.fragments.RecipePhoneFragment;
import com.example.android.bakingapp.fragments.RecipeTabletFragment;
import com.example.android.bakingapp.utilities.JsonUtility;
import com.example.android.bakingapp.utilities.RecipeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.RecipeOnClickListener{
//public class RecipeActivity extends AppCompatActivity{
    private static String JsonUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private int spanCount=1;
    @BindView(R.id.recycler_view)RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if( getResources().getBoolean(R.bool.isPhone)){ //created a bool resource file for sw600. It is true for phone and false for tablets
            spanCount=1;
            //RecipePhoneFragment fragment = new RecipePhoneFragment();
            //FragmentManager fm = getSupportFragmentManager();
            //fm.beginTransaction().add(R.id.fm_recipe_recycler, fragment).commit();
        }else{
            spanCount=2;
            //RecipeTabletFragment fragment = new RecipeTabletFragment();
            //FragmentManager fm = getSupportFragmentManager();
            //fm.beginTransaction().add(R.id.fm_recipe_recycler, fragment).commit();
        }


        ButterKnife.bind(this);
        mRecyclerView =findViewById(R.id.recycler_view);
        Configuration config = getResources().getConfiguration();
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
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
        intent.putExtra("recipeIndex", index);    //send the index of the recipes clicked to detail activity
        startActivity(intent);
    }
    public class GetRecipes extends AsyncTask<Void, Void, String[]>{
        String[] recipeNames = new String[4];
        @Override
        protected String[] doInBackground(Void... voids) {
            try{
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUtility.JsonUrl);
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
