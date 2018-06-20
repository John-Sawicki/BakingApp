package com.example.android.bakingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Utilities.JsonUtility;

public class RecipeActivity extends AppCompatActivity {
    private static String JsonUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }
    public class GetRecipes extends AsyncTask<Void, Void, String[]>{
        String[] recepieNames = new String[13];
        @Override
        protected String[] doInBackground(Void... voids) {
            try{
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUrl);
                recepieNames = JsonUtility.getRecipeJSON(jsonStringFromWeb);
                return recepieNames;
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
