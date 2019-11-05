package com.example.ccunia.bakingapp.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by ccunia on 8/10/2018.
 * reference
 * http://androidopentutorials.com/android-how-to-store-list-of-values-in-sharedpreferences/
 */

public class SharedPreference {

    public static final String PREFS_NAME = "ingredient_list";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }


    public void putIngredients(Context context, List<IngredientList> ingredients) {

        SharedPreferences ingredientList;
        SharedPreferences.Editor editor;

        ingredientList = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        editor = ingredientList.edit();

        Gson gson = new Gson();
        String json = gson.toJson(ingredients);

        editor.putString("ingredient", json);

        editor.apply();
    }

    public ArrayList<IngredientList> getIngredients(Context context) {
        SharedPreferences settings;
        List<IngredientList> retrievedList;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains("ingredient")) {
            String json = settings.getString("ingredient", null);
            Gson gson = new Gson();
            IngredientList[] ingredientItems = gson.fromJson(json,
                    IngredientList[].class);

            retrievedList = Arrays.asList(ingredientItems);
            retrievedList = new ArrayList<>(retrievedList);
        } else
            return null;

        return (ArrayList<IngredientList>) retrievedList;
    }

}
