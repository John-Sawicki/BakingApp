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
public class RecyclerViewTest {
    @Rule
    public ActivityTestRule<RecipeActivity>mActivityTestRule
            =new ActivityTestRule<>(RecipeActivity.class);
    /*@Test
    public void clickRecyclerView(){
            onData(anything()).inAdapterView(withId(R.id.recycler_view))
                    .atPosition(0).perform(click())
    }
    */
    @Test
    public void ParseJson_RetrieveRecipeList(){
        onData(startsWith("Recipe")).inAdapterView(withId(R.id.recycler_view))
                .atPosition(0).check(matches(withText("Recipe Introduction")));
    }

}
