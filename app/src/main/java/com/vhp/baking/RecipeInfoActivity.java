package com.vhp.baking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.vhp.baking.model.Recipe;
import com.vhp.baking.ui.RecipeInfoFragment;

import butterknife.ButterKnife;

public class RecipeInfoActivity extends AppCompatActivity{

    /*@BindView(R.id.recyclerView_recipe_steps)
    RecyclerView mRecipeRecyclerView;
    @BindView(R.id.textView_recipe_ingredients)
    TextView mIngredientsTextView;
*/
    private Recipe mUserSelectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        ButterKnife.bind(RecipeInfoActivity.this);

        mUserSelectedRecipe = getIntent().getExtras().getParcelable("Recipe");

        getSupportActionBar().setTitle(mUserSelectedRecipe.getName());

  /*      StringBuilder ingredients = new StringBuilder();
        for (int i = 0; i < mUserSelectedRecipe.getIngredients().size(); i++) {
            Log.d("RecipeInfoActivity", "onCreate: " + mUserSelectedRecipe.getIngredients().get(i).getIngredient());
            ingredients.append(mUserSelectedRecipe.getIngredients().get(i).getIngredient()).append("\n");
        }

        for (int i = 0; i < mUserSelectedRecipe.getSteps().size(); i++) {
            Log.d("RecipeInfoActivity", "onCreate: "+mUserSelectedRecipe.getSteps().get(i).getShortDescription());
        }
        mIngredientsTextView.setText(ingredients);
        mIngredientsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //setup recyclerView
        RecipeStepsAdapter mRecipeStepsAdapter  = new RecipeStepsAdapter(
                mUserSelectedRecipe.getSteps(),
                this
        );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                RecipeInfoActivity.this ,
                LinearLayoutManager.VERTICAL,
                false
        );

        mRecipeRecyclerView.setLayoutManager(linearLayoutManager);
        mRecipeRecyclerView.setAdapter(mRecipeStepsAdapter);
  */  }

   /* @Override
    public void onClick(List<Step> mRecipeList, int position) {
        Intent mIntent = new Intent(RecipeInfoActivity.this , RecipePlayActivity.class);
        mIntent.putExtra("Recipename" , mUserSelectedRecipe.getName());
        mIntent.putExtra("RecipeStepPosition" , position);
        mIntent.putParcelableArrayListExtra("RecipeSteps" , (ArrayList<Step>) mRecipeList);
        startActivity(mIntent);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        Fragment mRecipeFragment = new RecipeInfoFragment();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("Recipe" , mUserSelectedRecipe);
        mRecipeFragment.setArguments(mBundle);
        getSupportFragmentManager().beginTransaction().add(
                R.id.layout_recipe_container , mRecipeFragment
        ).commit();

    }

}
