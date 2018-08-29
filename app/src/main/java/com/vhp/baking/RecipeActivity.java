package com.vhp.baking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.vhp.baking.adapter.RecipeStepsAdapter;
import com.vhp.baking.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements RecipeStepsAdapter.RecipeOnClickHandler {

    @BindView(R.id.recyclerView_recipe_steps)
    RecyclerView mRecipeRecyclerView;
    @BindView(R.id.textView_recipe_ingredients)
    TextView mIngredientsTextView;

    private Recipe mUserSelectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(RecipeActivity.this);

        mUserSelectedRecipe = getIntent().getExtras().getParcelable("Recipe");

        getSupportActionBar().setTitle(mUserSelectedRecipe.getName());

        StringBuilder ingredients = new StringBuilder();
        for (int i = 0; i < mUserSelectedRecipe.getIngredients().size(); i++) {
            Log.d("RecipeActivity", "onCreate: " + mUserSelectedRecipe.getIngredients().get(i).getIngredient());
            ingredients.append(mUserSelectedRecipe.getIngredients().get(i).getIngredient()+"\n");
        }

        for (int i = 0; i < mUserSelectedRecipe.getSteps().size(); i++) {
            Log.d("RecipeActivity", "onCreate: "+mUserSelectedRecipe.getSteps().get(i).getShortDescription());
        }
        mIngredientsTextView.setText(ingredients);

        //setup recyclerView
        RecipeStepsAdapter mRecipeStepsAdapter  = new RecipeStepsAdapter(
                mUserSelectedRecipe.getSteps(),
                this
        );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                RecipeActivity.this ,
                LinearLayoutManager.VERTICAL,
                false
        );

        mRecipeRecyclerView.setLayoutManager(linearLayoutManager);
        mRecipeRecyclerView.setAdapter(mRecipeStepsAdapter);

    }

    @Override
    public void onClick(Recipe recipeInfo) {

    }
}