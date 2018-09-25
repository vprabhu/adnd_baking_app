package com.vhp.baking.ui;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vhp.baking.R;
import com.vhp.baking.RecipeInfoActivity;
import com.vhp.baking.adapter.RecipeStepsAdapter;
import com.vhp.baking.model.Recipe;
import com.vhp.baking.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeInfoFragment extends Fragment implements RecipeStepsAdapter.RecipeOnClickHandler {

    public RecipeInfoFragment() {
        // Required empty public constructor
    }
    @BindView(R.id.recyclerView_recipe_steps)
    RecyclerView mRecipeRecyclerView;
    @BindView(R.id.textView_recipe_ingredients)
    TextView mIngredientsTextView;
    private Unbinder mUnbinder;
    private Recipe mUserSelectedRecipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mUserSelectedRecipe = bundle.getParcelable("Recipe");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.activity_recipe, container, false);
        mUnbinder = ButterKnife.bind(this , rootView);

        StringBuilder ingredients = new StringBuilder();
        for (int i = 0; i < mUserSelectedRecipe.getIngredients().size(); i++) {
            Log.d("RecipeInfoActivity", "onCreate: " + mUserSelectedRecipe.getIngredients().get(i).getIngredient());
            ingredients.append(mUserSelectedRecipe.getIngredients().get(i).getIngredient()).append("\n");
        }
        mIngredientsTextView.setText(ingredients);

        //setup recyclerView
        RecipeStepsAdapter mRecipeStepsAdapter  = new RecipeStepsAdapter(
                mUserSelectedRecipe.getSteps(),
                this
        );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity() ,
                LinearLayoutManager.VERTICAL,
                false
        );

        mRecipeRecyclerView.setLayoutManager(linearLayoutManager);
        mRecipeRecyclerView.setAdapter(mRecipeStepsAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onClick(List<Step> mRecipeList, int position) {
        RecipePlayFragment mRecipePlayFragment = new RecipePlayFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("Recipename" , mUserSelectedRecipe.getName());
        mBundle.putInt("RecipeStepPosition" , position);
        mBundle.putParcelableArrayList("RecipeSteps" , (ArrayList<? extends Parcelable>) mRecipeList);
        mRecipePlayFragment.setArguments(mBundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.layout_recipe_container , mRecipePlayFragment
        ).addToBackStack(null).commit();
    }
}
