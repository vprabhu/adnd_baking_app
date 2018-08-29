package com.vhp.baking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vhp.baking.R;
import com.vhp.baking.model.Recipe;
import com.vhp.baking.model.Step;
import com.vhp.baking.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder>{
    private static final String TAG = "RecipeListAdapter";

    private List<Step> mRecipeList;
    private RecipeOnClickHandler mRecipeOnClickHandler;

    public RecipeStepsAdapter(List<Step> mRecipeList , RecipeOnClickHandler mRecipeOnClickHandlerParam) {
        this.mRecipeList = mRecipeList;
        mRecipeOnClickHandler = mRecipeOnClickHandlerParam;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface RecipeOnClickHandler {
        void onClick(List<Step> mRecipeList , int position);
    }


    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.layout_recipe_row_steps;
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflates the desired layout
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder recipeStepsViewHolder, int i) {
        recipeStepsViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textView_recipe_row_steps)
        TextView mRecipeTextView;
        @BindView(R.id.imageView_recipe_row_step_image)
        ImageView mRecipeImageView;

        RecipeStepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position){
            mRecipeTextView.setText(mRecipeList.get(position).getShortDescription());
            String path = mRecipeList.get(position).getThumbnailURL();
            if(!path.isEmpty()){
                Picasso.with(itemView.getContext()).load(path)
                        .into(mRecipeImageView);
            }
        }

        @Override
        public void onClick(View v) {
            mRecipeOnClickHandler.onClick(mRecipeList , getAdapterPosition());
        }
    }

    private String setDefaultForRecipes(int index){
        String defaultImagePath = null;
        switch (index){
            case 0:
                defaultImagePath = Constants.NUTELLA_PIE_IMAGE_PATH;
                break;
            case 1:
                defaultImagePath = Constants.BROWNIE_IMAGE_PATH;
                break;
            case 2:
                defaultImagePath = Constants.YELLOWCAKE_IMAGE_PATH;
                break;
            case 3:
                defaultImagePath = Constants.CHEESECAKE_IMAGE_PATH;
                break;
        }
        return defaultImagePath;
    }
}
