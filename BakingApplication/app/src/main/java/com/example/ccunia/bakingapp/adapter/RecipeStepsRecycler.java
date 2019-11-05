package com.example.ccunia.bakingapp.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccunia.bakingapp.R;
import com.example.ccunia.bakingapp.data.IngredientList;
import com.example.ccunia.bakingapp.data.RecipeList;
import com.example.ccunia.bakingapp.data.StepsRecipe;

import java.util.ArrayList;

/**
 * Created by CCunia on 7/3/2018.
 */

public class RecipeStepsRecycler extends RecyclerView.Adapter<RecipeStepsRecycler.RecipeStepsViewHolder> {

    Context mContext;
    ArrayList<IngredientList> mIngredients;
    ArrayList<StepsRecipe> mSteps;

    public RecipeStepsRecycler( ArrayList<IngredientList> ingredients) {
        this.mIngredients = ingredients;
        //this.mSteps = steps;
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvIngredients, tvMeasure, tvQuantity;
        //public final TextView tvSteps;

        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            tvIngredients = itemView.findViewById(R.id.tv_igredients);
            tvMeasure = itemView.findViewById(R.id.tv_measure);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            //tvSteps = itemView.findViewById(R.id.tv_steps);
        }
    }
    @Override
    public RecipeStepsRecycler.RecipeStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        int idListItem = R.layout.recipe_ingredient_list;
        boolean attachedToParent = false;
        View view = inflater.inflate(idListItem, parent, attachedToParent);
        return new RecipeStepsRecycler.RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepsRecycler.RecipeStepsViewHolder holder, int position) {

        holder.tvQuantity.setText(mIngredients.get(position).getQuantity().toString());
        holder.tvMeasure.setText(mIngredients.get(position).getMeasure());
        holder.tvIngredients.setText(mIngredients.get(position).getIngredient());

    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }


}
