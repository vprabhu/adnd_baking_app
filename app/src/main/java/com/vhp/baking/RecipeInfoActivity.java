package com.vhp.baking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.vhp.baking.adapter.RecipeStepsAdapter;
import com.vhp.baking.model.Recipe;
import com.vhp.baking.model.Step;
import com.vhp.baking.ui.RecipeInfoFragment;
import com.vhp.baking.ui.RecipePlayFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class RecipeInfoActivity extends AppCompatActivity
        implements RecipeInfoFragment.onIngredientClickListener {

    private Recipe mUserSelectedRecipe;
    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        ButterKnife.bind(RecipeInfoActivity.this);

        mUserSelectedRecipe = getIntent().getExtras().getParcelable("Recipe");

        getSupportActionBar().setTitle(mUserSelectedRecipe.getName());


    }



    @Override
    protected void onResume() {
        super.onResume();
        if(findViewById(R.id.layout_two_pane)!=null){
            isTwoPane = true;

            // initialize play fragment
            RecipePlayFragment mRecipePlayFragment = new RecipePlayFragment();
            Bundle mPLayBundle = new Bundle();
            mPLayBundle.putString("Recipename" , mUserSelectedRecipe.getName());
            mPLayBundle.putInt("RecipeStepPosition" , 0);
            mPLayBundle.putParcelableArrayList("RecipeSteps" , (ArrayList<? extends Parcelable>) mUserSelectedRecipe.getSteps());
            mRecipePlayFragment.setArguments(mPLayBundle);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.layout_recipe_play_fragment , mRecipePlayFragment
            ).commit();
        }else {
            isTwoPane = false;
        }
    }

    public Recipe getUserSelectedRecipe() {

        return mUserSelectedRecipe;
    }

    @Override
    public void onIngredientSelected(List<Step> mRecipeList, int position) {
        if(isTwoPane){
            // initialize play fragment
            RecipePlayFragment mRecipePlayFragment = new RecipePlayFragment();
            Bundle mPLayBundle = new Bundle();
            mPLayBundle.putInt("RecipeStepPosition" , position);
            mPLayBundle.putParcelableArrayList("RecipeSteps" , (ArrayList<? extends Parcelable>) mRecipeList);
            mRecipePlayFragment.setArguments(mPLayBundle);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.layout_recipe_play_fragment , mRecipePlayFragment
            ).commit();
        }else {
            Intent mIntent = new Intent(RecipeInfoActivity.this, RecipePlayActivity.class);
            mIntent.putExtra("Recipename" , mUserSelectedRecipe.getName());
            mIntent.putExtra("RecipeStepPosition" , position);
            mIntent.putParcelableArrayListExtra("RecipeSteps" , (ArrayList<Step>) mRecipeList);
            startActivity(mIntent);
        }
    }
}
