package com.example.android.bakingapp;


import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.android.bakingapp.Utilities.JsonUtility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetail extends AppCompatActivity {
    //@BindView(R.id.step_movie)VideoView stepVideo;
    //@BindView(R.id.no_video_image)ImageView noVideoImage;
    @Nullable @BindView(R.id.instruction_text)TextView instructionText; //only in phone view
    @Nullable @BindView(R.id.next_button) Button nextButton;    //only in phone view
    int recipeIndex; //ex brownies, this stays the same in this activity
    int stepIndex;  //ex step 2; increment to go to the next step by replacing a fragment
    int[] descAndUrl = new int[2];//first element is long description second is movie url
    StepFragment stepFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        descAndUrl[0] = getIntent().getIntExtra("recipeIndex",1);
        Log.d("recipeNumber",recipeIndex+"");
        descAndUrl[1] = getIntent().getIntExtra("stepIndex",1);
        Log.d("stepNumber",stepIndex+"");
        stepFragment = new StepFragment();

        stepFragment.setDescAndURL(descAndUrl);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.movie_step_fragment, stepFragment).commit();



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descAndUrl[0]+=descAndUrl[0];   //increment the step by increase the value in the JSON array to parse
                stepFragment.setDescAndURL(descAndUrl); //update the values for the fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_step_fragment, stepFragment).commit();  //replace the fragment with the new step description and video
            }
        });
    }
    /* this belongs in the fragment
    public class getStepsLong extends AsyncTask<Integer, Void,String[] >{
        String[] stepAndUrl = new String[2];//first element is long description second is movie url
        @Override
        protected String[] doInBackground(Integer... integers) {
            try{
                int recipeInt = integers[0];
                int stepInt= integers[1];
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUtility.JsonUrl);
                stepAndUrl = JsonUtility.getStepsLong(jsonStringFromWeb, recipeInt, stepInt);
                return stepAndUrl;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] strings) {
            instructionText.setText(strings[0]);
            super.onPostExecute(strings);
        }
    }
    */

}
