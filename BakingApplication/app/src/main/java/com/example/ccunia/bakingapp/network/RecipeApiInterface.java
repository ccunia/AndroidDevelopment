package com.example.ccunia.bakingapp.network;

import com.example.ccunia.bakingapp.data.RecipeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CCunia on 6/26/2018.
 */
import retrofit2.Call;
import retrofit2.http.GET;


public interface RecipeApiInterface {
    @GET("baking.json")
    Call<ArrayList<RecipeList>> getListRecipes();
}
