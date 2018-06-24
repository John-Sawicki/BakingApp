package com.example.android.bakingapp.Utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JsonUtility {
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
                Log.d("response from site", "url has input");
                return scanner.next();  //returns a string if it works
            } else {
                Log.d("response from site", "no response");
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
            JSONArray recipesJSON = new JSONArray(mRawJSON);
            for(int i = 0; i<recipes.length;i++){
                JSONObject recipeJSON = recipesJSON.getJSONObject(i);
                recipes[i]= recipeJSON.getString("name");
                Log.d("recipeNameUtil", recipes[i]);
            }
            return recipes;
        }catch (Exception e){
            e.printStackTrace(); return null;
        }
    }
    public static String getIngrediants(String rawJSON, int selectedRecipe) throws JSONException{
        String mRawJSON = rawJSON;
        int mSelectedRecipe = selectedRecipe;   //only retries the ingredients for the recipe that was clicked
        String ingredients="", quantity, measure, ingredient;
        try{
            JSONArray recipesJSON = new JSONArray(mRawJSON);
            JSONArray ingredientsArray = recipesJSON.getJSONArray(selectedRecipe);
            for(int i = 0; i<ingredientsArray.length(); i++){
                JSONObject oneIngrediant = ingredientsArray.getJSONObject(i);
                quantity= oneIngrediant.getString("quantity");
                measure =oneIngrediant.getString("measure");
                ingredient = oneIngrediant.getString("ingredient");
                ingredients+= quantity+"\t"+measure+"\t"+ingredient+"\n";   //display one ingredient per row
            }
            return ingredients;
        }catch (Exception e) {
            e.printStackTrace(); return null;
        }
    }
}
