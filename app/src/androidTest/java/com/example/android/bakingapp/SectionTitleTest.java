package com.example.android.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SectionTitleTest {
    @Rule
    public ActivityTestRule<RecipeActivity> mTestRule= new ActivityTestRule<>(RecipeActivity.class);
    @Test
    public void checkTitle(){
        onView((withId(R.id.recipe_title_text))).check(matches(withText("List of Recipes")));
    }

}
