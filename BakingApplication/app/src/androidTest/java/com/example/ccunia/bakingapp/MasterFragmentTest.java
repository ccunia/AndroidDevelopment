package com.example.ccunia.bakingapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import com.example.ccunia.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

//REFERENCE FROM: https://github.com/udacity/AdvancedAndroid_TeaTime/tree/TESP.04-Solution-AddIdlingResourceMenuActivityTest/app/src/androidTest/java/com/example/android/teatime
@RunWith(AndroidJUnit4.class)
public class MasterFragmentTest {
    public static final String INGREDIENT_NAME = "Ingredients";
    public static final String BACK_TO_RECIPE_BUTTON = "Back to Recipes";
    public static final String NEXT_TO_STEP_BUTTON = "Next to steps";


    @Rule
    public ActivityTestRule<MainActivity> masterFragmentTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a GridView item and checks it opens up the IngredientFragment list
     */
    @Test
    public void clickRecipeGridViewListAndStartIngredientFragment() {

        onView(withId(R.id.master_recipe_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Checks that the OrderActivity opens with the correct Ingredients label name displayed
        onView(withId(R.id.tv_label_ingredients)).check(matches(withText(INGREDIENT_NAME)));
        onView(withId(R.id.bt_back)).check(matches(withText(BACK_TO_RECIPE_BUTTON)));
        onView(withId(R.id.bt_next)).check(matches(withText(NEXT_TO_STEP_BUTTON)));


    }
}
