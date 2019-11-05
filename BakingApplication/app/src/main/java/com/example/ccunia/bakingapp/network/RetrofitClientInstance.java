package com.example.ccunia.bakingapp.network;

import com.example.ccunia.bakingapp.data.RecipeList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CCunia on 6/26/2018.
 */

public class RetrofitClientInstance {

    public static final String TAG = RetrofitClientInstance.class.getSimpleName();
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private static Retrofit retrofit;

    private Call<ArrayList<RecipeList>> recipeListCall;
    private ArrayList<RecipeList> recipeLists;

    //private DataCallback callback;
    public RetrofitClientInstance(){

    }

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
