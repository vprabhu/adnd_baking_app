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
import com.vhp.baking.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>{
    private static final String TAG = "RecipeListAdapter";

    private List<Recipe> mRecipeList;
    private RecipeOnClickHandler mRecipeOnClickHandler;

    public RecipeListAdapter(List<Recipe> mRecipeList , RecipeOnClickHandler mRecipeOnClickHandlerParam) {
        this.mRecipeList = mRecipeList;
        mRecipeOnClickHandler = mRecipeOnClickHandlerParam;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface RecipeOnClickHandler {
        void onClick(Recipe recipeInfo);
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
        recipeViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textView_recipe_name)
        TextView mRecipeTextView;
        @BindView(R.id.imageView_recipe_image)
        ImageView mRecipeImageView;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
//            mRecipeTextView = itemView.findViewById(R.id.textView_recipe_name);
//            mRecipeImageView = itemView.findViewById(R.id.imageView_recipe_image);
            itemView.setOnClickListener(this);
        }

        void bind(int position){
            mRecipeTextView.setText(mRecipeList.get(position).getName());
            String path = mRecipeList.get(position).getImage();
            if(!path.isEmpty()){
                Picasso.with(itemView.getContext()).load(path)
                        .into(mRecipeImageView);
            }else{
                Picasso.with(itemView.getContext())
                        .load(setDefaultForRecipes(position))
                        .into(mRecipeImageView);
            }
        }

        @Override
        public void onClick(View v) {
            mRecipeOnClickHandler.onClick(mRecipeList.get(getAdapterPosition()));
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
