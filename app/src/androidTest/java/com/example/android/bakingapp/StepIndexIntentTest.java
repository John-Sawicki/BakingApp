package com.example.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class StepIndexIntentTest {
    @Rule
    public IntentsTestRule<RecipeDetail> mTestRule = new IntentsTestRule<>(
            RecipeDetail.class);
    @Before
    public void stubRecipeStepIndexIntent(){
        Intent recipeStepIndex = new Intent();
        recipeStepIndex.putExtra("recipeIndex", 1);//Brownies
        recipeStepIndex.putExtra("stepIndex", 2);//melt butter
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, recipeStepIndex);
        intending(toPackage("com.example.android.bakingapp"))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,null));
    }
    @Test
    public void stepDetailIndexText(){
        Intent intent = new Intent();
        int recipeIndex =1, stepIndex = 2;
        intent.putExtra("recipeIndex",recipeIndex);
        intent.putExtra("stepIndex", stepIndex);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(toPackage("com.example.android.bakingapp")).respondWith(result);
        onView(withId(R.id.step_recycler_view)).perform(click());
    }
}
