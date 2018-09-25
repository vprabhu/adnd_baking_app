package com.vhp.baking.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.vhp.baking.RecipePlayActivity;
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
    private View rootView;

    private onIngredientClickListener mOnIngredientClickListener;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface onIngredientClickListener {
        void onIngredientSelected(List<Step> mRecipeList , int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mOnIngredientClickListener = (onIngredientClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        rootView = inflater.inflate(R.layout.activity_recipe, container, false);
        mUnbinder = ButterKnife.bind(this , rootView);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecipeInfoActivity mRecipeInfoActivity = (RecipeInfoActivity) getActivity();
        mUserSelectedRecipe = mRecipeInfoActivity.getUserSelectedRecipe();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onClick(List<Step> mRecipeList, int position) {
       /* RecipePlayFragment mRecipePlayFragment = new RecipePlayFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("Recipename" , mUserSelectedRecipe.getName());
        mBundle.putInt("RecipeStepPosition" , position);
        mBundle.putParcelableArrayList("RecipeSteps" , (ArrayList<? extends Parcelable>) mRecipeList);
        mRecipePlayFragment.setArguments(mBundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.layout_recipe_container , mRecipePlayFragment
        ).addToBackStack(null).commit();*/

       mOnIngredientClickListener.onIngredientSelected(mRecipeList , position);

        /*Intent mIntent = new Intent(getActivity(), RecipePlayActivity.class);
        mIntent.putExtra("Recipename" , mUserSelectedRecipe.getName());
        mIntent.putExtra("RecipeStepPosition" , position);
        mIntent.putParcelableArrayListExtra("RecipeSteps" , (ArrayList<Step>) mRecipeList);
        startActivity(mIntent);*/
    }
}
