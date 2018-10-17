package com.example.android.bakingapp;


import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetail extends AppCompatActivity {
    //@BindView(R.id.step_movie)VideoView stepVideo;
    //@BindView(R.id.no_video_image)ImageView noVideoImage;
    @Nullable @BindView(R.id.instruction_text)TextView instructionText; //only in phone view
    @Nullable @BindView(R.id.next_button) Button nextButton;    //only in phone view
    int recipeIndex; //ex brownies, this stays the same in this activity
    int stepIndex;  //ex step 2; increment to go to the next step by replacing a fragment
    private int[] recipeStepValues = new int[2];//first element is long description second is movie url
    //StepFragment stepFragment;
    private boolean phonePortrait = true, mBooleanRotation =false, mResueFragment;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation ==Configuration.ORIENTATION_LANDSCAPE){
            mBooleanRotation = true;
            Log.d("rotation", "to landscape "+mBooleanRotation);
        }else {
            mBooleanRotation= true;
            Log.d("rotation", "to portrait "+mBooleanRotation);
        }
    }
    FragmentManager fmAdd;
    StepFragment stepFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        if(findViewById(R.id.next_button)==null) phonePortrait =false;
        recipeStepValues[0] = getIntent().getIntExtra("recipeIndex",1);
        recipeStepValues[1] = getIntent().getIntExtra("stepIndex",1);
        Log.d("recStepDe Index","onCreate "+recipeStepValues[0]+" "+recipeStepValues[1]);

        if(savedInstanceState==null || mBooleanRotation){//don't add a fragment on rotation
            Log.d("recStepDe","rotate? "+mBooleanRotation);
             fmAdd = getSupportFragmentManager();
            stepFragment = new StepFragment();
            Bundle bundle = new Bundle();//pass the values to the fragment to use when it is first created
            bundle.putInt("recipeIndex",recipeStepValues[0]);
            bundle.putInt("stepIndex",recipeStepValues[1] );
            stepFragment.setArguments(bundle);
            Log.d("recStepDe Index","saved is null "+recipeStepValues[0]+" "+recipeStepValues[1]);
            fmAdd.beginTransaction().add(R.id.movie_step_fragment, stepFragment, "cooking fragment").commit();
        }else{
            fmAdd = getSupportFragmentManager();
            stepFragment = new StepFragment();
            Bundle bundle = new Bundle();//pass the values to the fragment to use when it is first created
            recipeStepValues[0]=savedInstanceState.getInt("saveRecipeKey");
            recipeStepValues[1]= savedInstanceState.getInt("saveStepKey");
            bundle.putInt("recipeIndex",recipeStepValues[0]);
            bundle.putInt("stepIndex",recipeStepValues[1] );
            stepFragment.setArguments(bundle);
            Log.d("recStepDe Index","saved isn't null "+recipeStepValues[0]+" "+recipeStepValues[1]);
            fmAdd.beginTransaction().replace(R.id.movie_step_fragment, stepFragment).commit();
        }
        mBooleanRotation = false;//once back in portrait mode set to false

        if(getResources().getBoolean(R.bool.isPhone)){
            Log.d("recStepDe","show button for portrait");
            nextButton.setOnClickListener(new View.OnClickListener() {  //replace the movie and instruction fragment when the next button is pressed
                @Override
                public void onClick(View view) {
                    recipeStepValues[1]=recipeStepValues[1]+1;   //increment the step by increase the value in the JSON array to parse
                    Log.d("recStepDe Index","onClick "+recipeStepValues[0]+" "+recipeStepValues[1]);
                    createFragment(recipeStepValues);
                }
            });
        }else{
            //nextButton.setVisibility(View.GONE);  null pointer when configChanges="orientation is removed from the manifest and there is no button in landscape xml
            Log.d("recStepDe","no button for landscape");
        }
    }
    private void createFragment(int[] recStepIndex){    //replaces the existing fragment when the Next button is pressed
        stepFragment = new StepFragment();
        stepFragment.setRetainInstance(true);  //saves values on rotation
        Bundle bundle2 = new Bundle();//pass the values to the fragment to use when it is first created
        bundle2.putInt("recipeIndex",recStepIndex[0]);
        bundle2.putInt("stepIndex",recStepIndex[1]);
        Log.d("recStepDe cF Index",recStepIndex[0]+" "+recStepIndex[1]);
        stepFragment.setArguments(bundle2);
        FragmentManager fm = getSupportFragmentManager();
        //stepFragmentClick.setDescAndURL(recipeStepValues); //update the values for the fragment
        fm.beginTransaction()
                .replace(R.id.movie_step_fragment, stepFragment).commit();  //replace the fragment with the new step description and video
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("saveRecipeKey", recipeStepValues[0]);
        outState.putInt("saveStepKey", recipeStepValues[1]);
        outState.putBoolean("reuseFragment", true);
        Log.d("recStepDe onSS Index",recipeStepValues[0]+" "+recipeStepValues[1]);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            recipeStepValues[0]= savedInstanceState.getInt("saveRecipeKey");
            recipeStepValues[1]= savedInstanceState.getInt("saveStepKey");
            mResueFragment = savedInstanceState.getBoolean("reuseFragment");
        }
        Log.d("recStepDe onRS index",recipeStepValues[0]+" "+recipeStepValues[1]);
    }
/*
    @Override
    protected void onStop() {
        super.onStop();
        fmAdd.beginTransaction().remove(stepFragment).commit();//add new fragment on rotation
    }
    */
}
