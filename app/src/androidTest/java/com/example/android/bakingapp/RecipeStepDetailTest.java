package com.example.android.bakingapp;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;
@RunWith(AndroidJUnit4.class)
public class RecipeStepDetailTest {
    @Rule
    public ActivityTestRule<RecipeStepDetail> mActivityTestRule
            =new ActivityTestRule<>(RecipeStepDetail.class);
    @Test
    public void ParseJson_RetrieveStepOne(){
        onData(startsWith("Recipe")).inAdapterView(withId(R.id.recycler_view))
                .atPosition(0).check(matches(withText("Recipe Introduction")));
    }
    @Test
    public void ParseJson_RetrieveStepTwo(){
        onData(startsWith("1")).inAdapterView(withId(R.id.recycler_view))
                .atPosition(1).check(matches(withText("1.")));
    }

}
