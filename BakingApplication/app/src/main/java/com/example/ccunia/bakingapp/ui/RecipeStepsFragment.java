package com.example.ccunia.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ccunia.bakingapp.R;
import com.example.ccunia.bakingapp.adapter.RecipeListRecycler;
import com.example.ccunia.bakingapp.adapter.RecipeStepsRecycler;
import com.example.ccunia.bakingapp.data.IngredientList;
import com.example.ccunia.bakingapp.data.RecipeList;
import com.example.ccunia.bakingapp.network.RecipeApiInterface;
import com.example.ccunia.bakingapp.network.RetrofitClientInstance;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by CCunia on 7/3/2018.
 */

public class RecipeStepsFragment extends Fragment {

    private final String TAG = RecipeStepsFragment.class.getSimpleName();
    public static final String RECIPE_STEPS = "steps";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String CURRENT_TITLE="title";
    RecyclerView mRecyclerView;
    RecipeStepsRecycler mAdapter;
    LinearLayoutManager mLinearLayoutManager;

    public RecipeStepsFragment() {
    }

    public static RecipeStepsFragment newInstance(ArrayList<IngredientList> ingredientList){

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RECIPE_INGREDIENTS,ingredientList);
        stepsFragment.setArguments(bundle);

        return stepsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps_fragment, container, false);
        ButterKnife.bind(this, (MainActivity)getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState!=null){
            getActivity().setTitle(savedInstanceState.getString(CURRENT_TITLE));
        }

        ArrayList<IngredientList> rIngredients = getArguments().getParcelableArrayList(RECIPE_INGREDIENTS);

                mRecyclerView = getView().findViewById(R.id.ingredients_recycler_view);
                mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mAdapter = new RecipeStepsRecycler(rIngredients);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CURRENT_TITLE, getActivity().getTitle().toString());
        super.onSaveInstanceState(outState);
    }

}
