package com.example.android.bakingapp;
import static android.app.Instrumentation.ActivityResult;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeIndexIntentTest {
    @Rule
    public IntentsTestRule<RecipeActivity> mTestRule = new IntentsTestRule<>(
            RecipeActivity.class);



      @Before
    public void stubRecipeIndexIntent(){
          Intent recipeDetail = new Intent();
          recipeDetail.putExtra("recipeId", 1);//Brownies
          ActivityResult result = new ActivityResult(Activity.RESULT_OK, recipeDetail);
          intending(toPackage("com.example.android.bakingapp"))
                  .respondWith(new ActivityResult(Activity.RESULT_OK,null));
    }
 /*
    @Test
    public void activityResult_RecipeIndex(){
        Intent indexIntent = new Intent();
        int index = 1;
        indexIntent.putExtra("recInex", index);
        ActivityResult result = new ActivityResult(Activity.RESULT_OK, indexIntent);
        intending(toPackage("com.example.android.bakingapp")).respondWith(result);
        onView(withId(R.id.recycler_view)).perform(click());
        onView(withId(R.id.step_recycler_view)).check(matches(withText("Recipe Introduction")));

    }
    */

    @Test
    public void validateRecipeIntent(){
        //onView(withId(R.id.recycler_view)).perform(click());

        onView(withId(R.id.recycler_view)).perform(click());
        intended(allOf(
                hasExtra("recipeId", 1),
                toPackage("com.example.android.bakingapp")
        ));
    }
}
