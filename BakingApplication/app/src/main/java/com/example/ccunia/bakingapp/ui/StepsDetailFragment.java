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
import com.example.ccunia.bakingapp.adapter.DetailStepsRecycler;
import com.example.ccunia.bakingapp.data.StepsRecipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CCunia on 7/5/2018.
 */

public class StepsDetailFragment extends Fragment implements DetailStepsRecycler.RecipeStepClickListener {


    private final String TAG = RecipeStepsFragment.class.getSimpleName();
    public static final String RECIPE_STEPS = "steps";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String CURRENT_TITLE="title";
    RecyclerView mRecyclerView;
    DetailStepsRecycler mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    ArrayList<StepsRecipe> rSteps;// = new ArrayList<>();
    //.ListItemClickListener mListItemClicked;
    @BindView(R.id.bt_back) Button backFragment;
    @BindView(R.id.bt_next) Button nextFragment;

    public StepsDetailFragment() {
    }

    public static StepsDetailFragment newInstance(ArrayList<StepsRecipe> steps){

        StepsDetailFragment detailFragment = new StepsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RECIPE_STEPS,steps);
        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_steps_fragment, container, false);
        ButterKnife.bind(this, (MainActivity)getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState!=null){
            getActivity().setTitle(savedInstanceState.getString(CURRENT_TITLE));
        }

        rSteps = getArguments().getParcelableArrayList(RECIPE_STEPS);

        mRecyclerView = getView().findViewById(R.id.steps_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new DetailStepsRecycler(this, rSteps);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onStepClicked(int position) {

        boolean tabletMode = ((MainActivity) getActivity()).isTablet();
        int layout;
        DisplayDetailedStepFragment detailedStepFragment = DisplayDetailedStepFragment.newInstance(rSteps, position);
                if(tabletMode){
                    layout = R.id.second_fragment;

                }else{
                    layout = R.id.master_fragment;
                }
        getFragmentManager().beginTransaction()
                .replace(layout, detailedStepFragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CURRENT_TITLE, getActivity().getTitle().toString());
        super.onSaveInstanceState(outState);
    }

}
