package com.example.ccunia.bakingapp.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccunia.bakingapp.R;
import com.example.ccunia.bakingapp.adapter.ClickListener;
import com.example.ccunia.bakingapp.adapter.RecipeListRecycler;
import com.example.ccunia.bakingapp.data.IngredientList;
import com.example.ccunia.bakingapp.data.RecipeList;
import com.example.ccunia.bakingapp.data.SharedPreference;
import com.example.ccunia.bakingapp.data.StepsRecipe;
import com.example.ccunia.bakingapp.network.RecipeApiInterface;
import com.example.ccunia.bakingapp.network.RetrofitClientInstance;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CCunia on 6/25/2018.
 */

public class MasterRecipeFragment extends Fragment implements RecipeListRecycler.ListItemClickListener{

    private final String TAG = MasterRecipeFragment.class.getSimpleName();
    RecyclerView mRecyclerView;
    RecipeListRecycler mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    RecipeListRecycler.ListItemClickListener mListItemClicked;
    FrameLayout sFragment;
    ClickListener mListener;

    ArrayList<RecipeList> recipe = new ArrayList<>();
    ArrayList<IngredientList> ingredient = new ArrayList<>();
    ArrayList<StepsRecipe> steps = new ArrayList<>();

    private final String BACK_TO_RECIPE = "Back to Recipes";
    private final String BACK_TO_INGREDIENT = "Back to Ingredients";
    private final String NEXT_TO_STEP = "Next to steps";
    private final String BACK_TO_STEP = "Back to Steps";
    private final String NAME_RECIPE = "name";
    private final String INGREDIENTS_TAG = "ingredients";
    private final String STEPS_TAG = "steps";
    public static final String CURRENT_TITLE="title";

    @BindView(R.id.bt_back) Button backFragment;
    @BindView(R.id.bt_next) Button nextFragment;
    @BindView(R.id.master_fragment) View masterFragment;
    @BindView(R.id.second_fragment) View secondFragment;
    @BindView(R.id.third_fragment) View thirdFragment;

    public MasterRecipeFragment() {

    }

    public static MasterRecipeFragment newInstance(ClickListener listener){
        MasterRecipeFragment mRecipeFragment = new MasterRecipeFragment();
        mRecipeFragment.mListener=listener;
        return mRecipeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.master_recipe_fragment, container, false);
        mListItemClicked = (RecipeListRecycler.ListItemClickListener)this;
        ButterKnife.bind(this, (MainActivity)getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG,"fragment tag "+getTag());
        if (savedInstanceState!=null){
            getActivity().setTitle(savedInstanceState.getString(CURRENT_TITLE));

        }

        RecipeApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(RecipeApiInterface.class);
        Call<ArrayList<RecipeList>> call = service.getListRecipes();
        call.enqueue(new Callback<ArrayList<RecipeList>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeList>> call, Response<ArrayList<RecipeList>> response) {


                recipe = response.body();

                mRecyclerView = getView().findViewById(R.id.master_recipe_recycler_view);
                if (((MainActivity) getActivity()).isTablet()){
                    GridLayoutManager mGridlayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL,false);
                    mRecyclerView.setLayoutManager(mGridlayoutManager);
                }else {
                    mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                }
                mAdapter = new RecipeListRecycler(mListItemClicked, response.body());
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<RecipeList>> call, Throwable t) {
                Toast.makeText(getContext(),"onFailure", Toast.LENGTH_SHORT);
            }
        });
        onRecipeOption(view);
    }

    @Override
    public void onItemClicked(int position) {

        String recipeName = recipe.get(position).getName();
        getActivity().setTitle(recipeName);

        ingredient =recipe.get(position).getIngredients();
        steps = recipe.get(position).getSteps();

        mListener.onClickListener(steps);

        StringBuilder builder = new StringBuilder();
        for(int i =0; i<ingredient.size(); i++) {
            String item = ingredient.get(i).getIngredient();
            builder.append(item);
            builder.append("\n");

        }

        passToWidget(ingredient,recipeName, builder.toString());

        startIngredientsFragment();

    }



    public void startIngredientsFragment(){

        RecipeStepsFragment recipeSteps = RecipeStepsFragment.newInstance(ingredient);
        if (((MainActivity) getActivity()).isTablet()){
            secondFragment.setVisibility(View.VISIBLE);
            masterFragment.setVisibility(View.VISIBLE);
            thirdFragment.setVisibility(View.GONE);
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.master_fragment, recipeSteps, INGREDIENTS_TAG)
                .addToBackStack(null)
                .commit();
    }
    public void startStepsFragment(){

        StepsDetailFragment detailSteps = StepsDetailFragment.newInstance(steps);

        getFragmentManager().beginTransaction()
                .replace(R.id.master_fragment, detailSteps, STEPS_TAG)
                .addToBackStack(null)
                .commit();
    }

    public void onRecipeOption(View view){
        backFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentSelection = backFragment.getText().toString();

                switch (currentSelection){
                    case BACK_TO_INGREDIENT : getFragmentManager().popBackStack(1, FragmentManager.POP_BACK_STACK_INCLUSIVE);break;
                    case BACK_TO_RECIPE: getFragmentManager().popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);break;
                    case BACK_TO_STEP: getFragmentManager().popBackStack(2, FragmentManager.POP_BACK_STACK_INCLUSIVE);break;
                    default:getFragmentManager().popBackStack();break;
                }

            }
        });

        nextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStepsFragment();

            }
        });
    }

    public void passToWidget(ArrayList<IngredientList> ingredients, String recipeName, String ingred){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        RemoteViews remoteViews = new RemoteViews(getContext().getPackageName(), R.layout.recipe_ingredient_widget);
        ComponentName currentWidget = new ComponentName(getContext(), RecipeIngredientWidget.class);

        remoteViews.setTextViewText(R.id.appwidget_text, recipeName);
        remoteViews.setTextViewText(R.id.tv_widget_ingredient, ingred);

         Log.d(TAG, recipeName);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ingredient", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_RECIPE, recipeName);
        editor.apply();

        SharedPreference mPref = new SharedPreference();
        mPref.putIngredients(getContext(),new ArrayList<>(ingredients));

        appWidgetManager.updateAppWidget(currentWidget, remoteViews);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString(CURRENT_TITLE, getActivity().getTitle().toString());
        super.onSaveInstanceState(outState);
    }

}
