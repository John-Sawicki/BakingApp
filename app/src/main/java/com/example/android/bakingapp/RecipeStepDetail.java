package com.example.android.bakingapp;


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
    int[] recipeStepValues = new int[2];//first element is long description second is movie url
    //StepFragment stepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        recipeStepValues[0] = getIntent().getIntExtra("recipeIndex",1);
        Log.d("recipeNumberStepDetail",recipeStepValues[0]+"");
        recipeStepValues[1] = getIntent().getIntExtra("stepIndex",1);
        Log.d("stepNumberStepDetail",recipeStepValues[1]+"");

        //StepFragment stepFragmentOnCreate = new StepFragment();
        if(savedInstanceState==null){
            FragmentManager fmAdd = getSupportFragmentManager();
            StepFragment stepFragmentOnCreate = new StepFragment();
            Bundle bundle = new Bundle();//pass the values to the fragment to use when it is first created
            bundle.putInt("recipeIndex",recipeStepValues[0]);
            bundle.putInt("stepIndex",recipeStepValues[1] );
            stepFragmentOnCreate.setArguments(bundle);
            //stepFragmentOnCreate.setDescAndURL(recipeStepValues);

            Log.d("steDetailAddFragment","saved is null");
            fmAdd.beginTransaction().add(R.id.movie_step_fragment, stepFragmentOnCreate).commit();
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeStepValues[1]=recipeStepValues[1]+1;   //increment the step by increase the value in the JSON array to parse
                createFragment(recipeStepValues);
                /*
                StepFragment stepFragmentClick = new StepFragment();
                Bundle bundle2 = new Bundle();//pass the values to the fragment to use when it is first created
                bundle2.putInt("recipeIndex click",recipeStepValues[0]);
                Log.d("StepDetail onClickRec",recipeStepValues[0]+"");
                bundle2.putInt("stepIndex click",recipeStepValues[1]);
                Log.d("StepDetail onClickStp",recipeStepValues[1]+"");
                stepFragmentClick.setArguments(bundle2);
                FragmentManager fm = getSupportFragmentManager();
                //stepFragmentClick.setDescAndURL(recipeStepValues); //update the values for the fragment
                fm.beginTransaction()
                        .replace(R.id.movie_step_fragment, stepFragmentClick).commit();  //replace the fragment with the new step description and video
            */
            }
        });
    }
    private void createFragment(int[] recStepIndex){
        StepFragment stepFragmentClick = new StepFragment();
        Bundle bundle2 = new Bundle();//pass the values to the fragment to use when it is first created
        bundle2.putInt("recipeIndex",recStepIndex[0]);
        Log.d("StepDetail onClickRec",recStepIndex[0]+"");
        bundle2.putInt("stepIndex",recStepIndex[1]);
        Log.d("StepDetail onClickStp",recStepIndex[1]+"");
        stepFragmentClick.setArguments(bundle2);
        FragmentManager fm = getSupportFragmentManager();
        //stepFragmentClick.setDescAndURL(recipeStepValues); //update the values for the fragment
        fm.beginTransaction()
                .replace(R.id.movie_step_fragment, stepFragmentClick).commit();  //replace the fragment with the new step description and video

    }
}
