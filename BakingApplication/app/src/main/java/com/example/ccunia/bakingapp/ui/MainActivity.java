package com.example.ccunia.bakingapp.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ccunia.bakingapp.R;
import com.example.ccunia.bakingapp.adapter.ClickListener;
import com.example.ccunia.bakingapp.data.RecipeList;
import com.example.ccunia.bakingapp.data.StepsRecipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.FragmentManager.OnBackStackChangedListener, ClickListener{

    private final String TAG = MainActivity.class.getSimpleName();
    private final String MAIN_TITLE = "Baking App";
    private final String BACK_TO_RECIPE = "Back to Recipes";
    private final String BACK_TO_INGREDIENT = "Back to Ingredients";
    private final String NEXT_TO_STEP = "Next to steps";
    private final String BACK_TO_STEP = "Back to Steps";
    private final String FRECORD_TAG = "fRecord";
    private final String FSIZE_TAG = "fSize";
    private final String MASTER_TAG = "master";
    private final String STEP_TAG = "step";
    public static final String CURRENT_TAG="tag";
    String currentTag;
    int fragmentRecord;
    int fragmentSize;
    MasterRecipeFragment masterRecipe;
    ArrayList<StepsRecipe> mSteps;

    @BindView(R.id.bt_back) Button backFragment;
    @BindView(R.id.bt_next) Button nextFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(MAIN_TITLE);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        masterRecipe = MasterRecipeFragment.newInstance(this);
        int layout;
        if(isTablet()){

            layout = R.id.third_fragment;
            findViewById(layout).setVisibility(View.VISIBLE);
            findViewById(R.id.master_fragment).setVisibility(View.GONE);

        }else{
            layout = R.id.master_fragment;
        }

        if (savedInstanceState!=null){
            final ArrayList<StepsRecipe> retrieveSteps = savedInstanceState.getParcelableArrayList(STEP_TAG);
            fragmentRecord = savedInstanceState.getInt(FRECORD_TAG);
            fragmentSize = savedInstanceState.getInt(FSIZE_TAG);
            setupControls();

            final Fragment fr = getSupportFragmentManager().findFragmentByTag(MASTER_TAG);
            if (fr != null){
                backFragment.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       fr.getFragmentManager().popBackStack();
                   }
               });
                nextFragment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mSteps = savedInstanceState.getParcelableArrayList("step");
                        if (retrieveSteps.size() != 0) {
                            fr.getFragmentManager().beginTransaction()
                                    .replace(R.id.master_fragment, StepsDetailFragment.newInstance(retrieveSteps), "steps")
                                    .addToBackStack(null)
                                    .commit();
                        }
                        //Log.d(TAG,"retrieve Steps "+retrieveSteps.isEmpty());
                    }
                });
                mSteps=retrieveSteps;
            }
            String gettingTag = savedInstanceState.getString(CURRENT_TAG);Log.d(TAG,"getting tag from SAVE "+gettingTag);
            Fragment saveFragment = getSupportFragmentManager().findFragmentByTag(gettingTag);
            getSupportFragmentManager().beginTransaction().show(saveFragment);
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(layout, masterRecipe, MASTER_TAG)
//                .add(R.id.master_fragment, masterRecipe)
                    .commit();
        }

    }

    @Override
    public void onBackStackChanged() {

        fragmentRecord = getSupportFragmentManager().getBackStackEntryCount();
        fragmentSize = getSupportFragmentManager().getFragments().size();
        if (isTablet()){
            currentTag = getSupportFragmentManager().getFragments().get(fragmentSize - 1).getTag();
        }else {
            currentTag = getSupportFragmentManager().getFragments().get(fragmentSize - 1).getTag();
        }
        setupControls();
    }

    /*
    The isTablet function is taken as a reference from stackOverflow
    https://stackoverflow.com/questions/16784101/how-to-find-tablet-or-phone-in-android-programmatically
     */
    public boolean isTablet(){
        boolean xlarge = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        boolean SW600    = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL);
        return (xlarge || large || SW600);
    }
    public void setupControls(){

        if(isTablet() & fragmentRecord==0){
            backFragment.setVisibility(View.GONE);
            nextFragment.setVisibility(View.GONE);
            findViewById(R.id.third_fragment).setVisibility(View.VISIBLE);
            findViewById(R.id.master_fragment).setVisibility(View.GONE);
            setTitle(MAIN_TITLE);

        }else if(isTablet() & fragmentRecord==1){
            backFragment.setText(BACK_TO_RECIPE);
            nextFragment.setText(NEXT_TO_STEP);

            backFragment.setVisibility(View.VISIBLE);
            nextFragment.setVisibility(View.VISIBLE);
            findViewById(R.id.third_fragment).setVisibility(View.GONE);
            findViewById(R.id.master_fragment).setVisibility(View.VISIBLE);
        }else if (fragmentRecord==1){
            backFragment.setText(BACK_TO_RECIPE);
            nextFragment.setText(NEXT_TO_STEP);

            backFragment.setVisibility(View.VISIBLE);
            nextFragment.setVisibility(View.VISIBLE);
        }else if(fragmentRecord == 2) {
            backFragment.setText(BACK_TO_INGREDIENT);

            backFragment.setVisibility(View.VISIBLE);
            nextFragment.setVisibility(View.GONE);
        }else if(fragmentSize==1 & fragmentRecord == 3) {
            backFragment.setText(BACK_TO_STEP);

            backFragment.setVisibility(View.VISIBLE);
            nextFragment.setVisibility(View.GONE);
        }else if(fragmentSize==3 & fragmentRecord >= 3) {
            backFragment.setText(BACK_TO_INGREDIENT);

            backFragment.setVisibility(View.VISIBLE);
            nextFragment.setVisibility(View.GONE);
            findViewById(R.id.third_fragment).setVisibility(View.GONE);
            findViewById(R.id.master_fragment).setVisibility(View.VISIBLE);
        }else{
            backFragment.setVisibility(View.GONE);
            nextFragment.setVisibility(View.GONE);
            setTitle(MAIN_TITLE);

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(CURRENT_TAG, currentTag);
        outState.putInt(FRECORD_TAG, fragmentRecord);
        outState.putInt(FSIZE_TAG, fragmentSize);

        outState.putParcelableArrayList(STEP_TAG, mSteps);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClickListener(ArrayList<StepsRecipe> steps) {
        mSteps = steps;
    }
}
