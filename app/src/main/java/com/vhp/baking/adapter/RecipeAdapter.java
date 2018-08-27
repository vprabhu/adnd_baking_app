package com.vhp.baking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vhp.baking.R;
import com.vhp.baking.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    private static final String TAG = "RecipeAdapter";

    private List<Recipe> mRecipeList;

    public RecipeAdapter(List<Recipe> mRecipeList) {
        this.mRecipeList = mRecipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.layout_recipe_row;
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflates the desired layout
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        recipeViewHolder.mRecipeTextView.setText(mRecipeList.get(i).getName());
        Log.d(TAG, "onBindViewHolder: "+ mRecipeList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        TextView mRecipeTextView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeTextView = itemView.findViewById(R.id.textView_recipe_name);
        }
    }
}
