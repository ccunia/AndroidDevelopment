package com.example.ccunia.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccunia.bakingapp.R;
import com.example.ccunia.bakingapp.data.RecipeList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by CCunia on 6/25/2018.
 */

public class RecipeListRecycler extends RecyclerView.Adapter<RecipeListRecycler.RecipeListViewHolder> {
    Context mContext;
    ArrayList<RecipeList> mRecipeList ;
    final private ListItemClickListener mItemClicked;

    public interface ListItemClickListener{
        void onItemClicked(int position);
    }

    public RecipeListRecycler(ListItemClickListener listener, ArrayList<RecipeList> recipeList){

        this.mRecipeList = recipeList;
        this.mItemClicked = listener;
    }

    public void setRecipeList(ArrayList<RecipeList> recipeList){
        mRecipeList = recipeList;
        notifyDataSetChanged();

    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mRecipeImageView;
        public final TextView mRecipeName;

        public RecipeListViewHolder(View itemView) {
            super(itemView);

            mRecipeImageView = itemView.findViewById(R.id.iv_recipe);
            mRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mItemClicked.onItemClicked(adapterPosition);

        }
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        int recipeIdListItem = R.layout.master_recipe_list;
        boolean attachedToParent = false;
        View view = inflater.inflate(recipeIdListItem, parent, attachedToParent);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {

        //mList.add(R.mipmap.ic_launcher_round);
        holder.mRecipeName.setText(mRecipeList.get(position).getName());
        String thumbnail = mRecipeList.get(position).getImage();
        if (thumbnail!=null && thumbnail.trim().isEmpty()){
            thumbnail = null;
        }
        Picasso.with(mContext)
                .load(thumbnail)
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                //.resize(800,581)
                .centerInside()
                .noFade()
                .into(holder.mRecipeImageView);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }


}
