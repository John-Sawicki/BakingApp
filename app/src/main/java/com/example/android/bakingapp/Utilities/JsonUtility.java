package com.example.android.bakingapp.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JsonUtility {
    public static String JsonUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static String getResponseFromSite(String stringUrl)throws IOException {
        Uri builtUri = Uri.parse(stringUrl);
        URL url = new URL(builtUri.toString());
        HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                Log.d("stepsUtility fromSite", "url has input");
                return scanner.next();  //returns a string if it works
            } else {
                Log.d("stepsUtility fromSite", "no response");
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
    public static String[] getRecipeJSON(String rawJSON) throws JSONException{
        String mRawJSON = rawJSON;
        String[] recipes = new String[4];
        try{
            JSONArray recipesJSON = new JSONArray(mRawJSON);    //has all recipes
            for(int i = 0; i<recipes.length;i++){
                JSONObject recipeJSON = recipesJSON.getJSONObject(i);   //when i =0 has only nutella pie
                recipes[i]= recipeJSON.getString("name");   //get key pair for name
                Log.d("stepsUtility recipe", recipes[i]);
            }
            return recipes;
        }catch (JSONException e){
            e.printStackTrace(); return null;
        }
    }
    public static String getIngredients(String rawJSON, int selectedRecipe) throws JSONException{
        String mRawJSON = rawJSON;
        int mSelectedRecipe = selectedRecipe;   //only retries the ingredients for the recipe that was clicked
        String ingredients="", quantity, measure, ingredient;
        try{
            JSONArray recipesJSON = new JSONArray(mRawJSON);
            JSONObject recipeJSON = recipesJSON.getJSONObject(selectedRecipe);   //only has 1 recipe
            JSONArray ingredientsArray = recipeJSON.getJSONArray("ingredients");
            for(int i = 0; i<ingredientsArray.length(); i++){
                JSONObject oneIngredient = ingredientsArray.getJSONObject(i);   //get first ingredient
                    quantity= oneIngredient.getString("quantity");
                    measure =oneIngredient.getString("measure");
                    ingredient = oneIngredient.getString("ingredient");
                    ingredients+= quantity+measure+"\t\t"+ingredient+"\n";   //display one ingredient per row
            }
            return ingredients;
        }catch (Exception e) {
            e.printStackTrace(); return null;
        }
    }
    public static String[] getStepsShort(String rawJSON, int selectedRecipe) throws JSONException{
        String mRawJSON = rawJSON;
        int arrayLenth =determineArrayLength(selectedRecipe);
        String[] steps =new String[arrayLenth];//null;//    new String[20];
        try {
            JSONArray recipesJSON = new JSONArray(mRawJSON);
            JSONObject recipeJSON = recipesJSON.getJSONObject(selectedRecipe);   //only has 1 recipe
            JSONArray stepArray = recipeJSON.getJSONArray("steps");
            for(int i = 0; i<stepArray.length(); i++) {
                JSONObject oneStep = stepArray.getJSONObject(i);   //get first ingredient
                //steps = new String[stepArray.length()]; //length depends on the recipe
                steps[i] = oneStep.getString("shortDescription");
                Log.d("stepsUtility", steps[i]);
                //steps[i]+="\n"+oneStep.getString("description");
            }
            return steps;
        }catch (Exception e){
            e.printStackTrace(); return null;
        }
    }
    public static String[] getStepsLong(String rawJSON, int selectedRecipe, int selectedStep, Context context) throws JSONException{
        String mRawJSON = rawJSON;
        String step[] = {"empty", "empty"};//first element is long description second is movie url
        try {
            JSONArray recipesJSON = new JSONArray(mRawJSON);
            JSONObject recipeJSON = recipesJSON.getJSONObject(selectedRecipe);   //only has 1 recipe
            JSONArray stepArray = recipeJSON.getJSONArray("steps");
            if(selectedStep>=stepArray.length()){//at the last step tell the user there are no more steps
                step[0]=context.getResources().getString(R.string.no_more_steps);
                step[1]="";
                return step;
            }else{
                JSONObject oneStep = stepArray.getJSONObject(selectedStep);
                step[0] = oneStep.getString("description");
                if(oneStep.getString("videoURL")==null){
                    step[1]="empty"; Log.d("stepsUtility", "null url");
                }else if(oneStep.getString("videoURL")==""){
                    step[1]="empty"; Log.d("stepsUtility", "empty url");
                }else{
                    step[1]=oneStep.getString("videoURL");
                }
                Log.d("stepsUtility Des", step[0]+" "+step[1]);
                return step;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private static int determineArrayLength(int selectedRecipe){
            switch (selectedRecipe){
                case 0: return 7;
                case 1: return 10;
                case 2: return 14;
                case 3: return 13;
                default: return 14;
            }
    }
}
