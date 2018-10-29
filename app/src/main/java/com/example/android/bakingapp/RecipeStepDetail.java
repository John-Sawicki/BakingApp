package com.example.android.bakingapp;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetail extends AppCompatActivity {
    @Nullable @BindView(R.id.instruction_text)TextView instructionText; //only in phone view
    @Nullable @BindView(R.id.next_button) Button nextButton;    //only in phone view
    private int[] recipeStepValues = new int[2];//first element is long description second is movie url
    private boolean phonePortrait = true, mBooleanRotation =false, mResueFragment;
    private String TIME_KEY ="timeKey", KEY_VIDEO_STATE="KEY_VIDEO_STATE";
    private long movieTime= 0;
    private int playbackState;
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
        Log.d("RSD Index","onCreate "+recipeStepValues[0]+" "+recipeStepValues[1]);
        if(savedInstanceState==null || mBooleanRotation){//don't add a fragment on rotation
            Log.d("RSD","rotate? "+mBooleanRotation);
             fmAdd = getSupportFragmentManager();
            stepFragment = new StepFragment();
            Bundle bundle = new Bundle();//pass the values to the fragment to use when it is first created
            bundle.putInt("recipeIndex",recipeStepValues[0]);
            bundle.putInt("stepIndex",recipeStepValues[1] );
            bundle.putInt(TIME_KEY,0);//start new videos at 0 seconds
            stepFragment.setArguments(bundle);
            Log.d("RSD onCreate","saved is null "+recipeStepValues[0]+" "+recipeStepValues[1]);
            fmAdd.beginTransaction().add(R.id.movie_step_fragment, stepFragment, "cooking fragment").commit();
        }else{
            fmAdd = getSupportFragmentManager();
            stepFragment = new StepFragment();
            Bundle bundle = new Bundle();//pass the values to the fragment to use when it is first created
            recipeStepValues[0]=savedInstanceState.getInt("saveRecipeKey");
            recipeStepValues[1]= savedInstanceState.getInt("saveStepKey");
            movieTime =savedInstanceState.getLong(TIME_KEY);
            playbackState = savedInstanceState.getInt(KEY_VIDEO_STATE);
            bundle.putInt("recipeIndex", recipeStepValues[0]);
            bundle.putInt("stepIndex", recipeStepValues[1]);
            bundle.putLong(TIME_KEY, movieTime);
            bundle.putInt(KEY_VIDEO_STATE, playbackState);
            stepFragment.setArguments(bundle);
            Log.d("RSD onCreate","saved isn't null "+recipeStepValues[0]+" "+recipeStepValues[1]+" time "+movieTime+" state "+playbackState);
            fmAdd.beginTransaction().replace(R.id.movie_step_fragment, stepFragment).commit();
        }
        mBooleanRotation = false;//once back in portrait mode set to false
        if(getResources().getBoolean(R.bool.isPhone)){
            Log.d("RSD","show button for portrait");
            nextButton.setOnClickListener(new View.OnClickListener() {  //replace the movie and instruction fragment when the next button is pressed
                @Override
                public void onClick(View view) {
                    recipeStepValues[1]=recipeStepValues[1]+1;   //increment the step by increase the value in the JSON array to parse
                    Log.d("RSD Index","onClick "+recipeStepValues[0]+" "+recipeStepValues[1]);
                    createFragment(recipeStepValues);
                }
            });
        }else{
            Log.d("RSD","no button for landscape");
        }
    }
    private void createFragment(int[] recStepIndex){    //replaces the existing fragment when the Next button is pressed
        stepFragment = new StepFragment();
        stepFragment.setRetainInstance(true);  //saves values on rotation
        Bundle bundle2 = new Bundle();//pass the values to the fragment to use when it is first created
        bundle2.putInt("recipeIndex",recStepIndex[0]);
        bundle2.putInt("stepIndex",recStepIndex[1]);
        bundle2.putLong(TIME_KEY,movieTime );
        bundle2.putInt(TIME_KEY,0);
        Log.d("RSD cF Index",recStepIndex[0]+" "+recStepIndex[1]);
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
        Bundle videoInfo = getIntent().getExtras();
        if(videoInfo!=null){    //get video info from the fragment
            movieTime = videoInfo.getLong(TIME_KEY);
            playbackState = videoInfo.getInt(KEY_VIDEO_STATE);
            Log.d("RSD onSaveI", "bundle isn't null "+movieTime+" state "+playbackState);
        }
        outState.putLong(TIME_KEY,movieTime);
        outState.putInt(KEY_VIDEO_STATE, playbackState);
        Log.d("RSD onSaveI",recipeStepValues[0]+" "+recipeStepValues[1]+" time "+movieTime);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            recipeStepValues[0]= savedInstanceState.getInt("saveRecipeKey");
            recipeStepValues[1]= savedInstanceState.getInt("saveStepKey");
            mResueFragment = savedInstanceState.getBoolean("reuseFragment");
            movieTime = savedInstanceState.getLong(TIME_KEY, 0);
            playbackState = savedInstanceState.getInt(KEY_VIDEO_STATE, 0);
        }
        Log.d("RSD onRestI",recipeStepValues[0]+" "+recipeStepValues[1]+" time "+movieTime+" time "+movieTime);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.to_receipe_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if(itemClicked == R.id.menu_to_recipe_act){
            Log.d("RSD menu clicked", "go to recipe activity");
            startActivity(new Intent(this, RecipeActivity.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("RSD onResume",movieTime+"");
    }
}
