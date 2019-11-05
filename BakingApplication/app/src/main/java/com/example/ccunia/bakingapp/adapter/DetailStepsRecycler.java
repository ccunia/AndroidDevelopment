package com.example.ccunia.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ccunia.bakingapp.R;
import com.example.ccunia.bakingapp.data.IngredientList;
import com.example.ccunia.bakingapp.data.StepsRecipe;

import java.util.ArrayList;

/**
 * Created by CCunia on 7/6/2018.
 */

public class DetailStepsRecycler extends RecyclerView.Adapter<DetailStepsRecycler.DetailStepsViewHolder> {

    Context mContext;
    //ArrayList<IngredientList> mIngredients;
    ArrayList<StepsRecipe> mSteps;
    final private RecipeStepClickListener mRecipeStepClicked;

    public interface RecipeStepClickListener{
//        void onStepClicked(String description, String video);
        void onStepClicked(int position);
    }

    public DetailStepsRecycler(RecipeStepClickListener listener, ArrayList<StepsRecipe> steps) {
        this.mSteps = steps;
        this.mRecipeStepClicked = listener;
    }

    public class DetailStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView tvSteps;

        public DetailStepsViewHolder(View itemView) {
            super(itemView);
            tvSteps = itemView.findViewById(R.id.tv_steps);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String getDescription = mSteps.get(adapterPosition).getDescription();
            String getVideo = mSteps.get(adapterPosition).getVideoUrl();
//            mRecipeStepClicked.onStepClicked(getDescription, getVideo);
            mRecipeStepClicked.onStepClicked(adapterPosition);
        }
    }

    @Override
    public DetailStepsRecycler.DetailStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        int idListItem = R.layout.recipe_step_list;
        boolean attachedToParent = false;
        View view = inflater.inflate(idListItem, parent, attachedToParent);
        return new DetailStepsRecycler.DetailStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailStepsRecycler.DetailStepsViewHolder holder, int position) {

        holder.tvSteps.setText(mSteps.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }


}
