package com.vhp.baking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vhp.baking.model.Step;
import com.vhp.baking.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipePlayActivity extends AppCompatActivity {

    @BindView(R.id.button_next)
    Button mNextButton;
    @BindView(R.id.button_prev)
    Button mPrevButton;
    @BindView(R.id.textView_step_info)
    TextView mStepNumberTextView;
    @BindView(R.id.textView_recipe_steps_expanded)
    TextView mStepDescriptionTextView;
    @BindView(R.id.imageView_recipe_row_image)
    ImageView mStepVideoImageView;
    private ArrayList<Step> mStepList;
    private int recipeStepNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_play);
        ButterKnife.bind(RecipePlayActivity.this);

        String recipeName = getIntent().getStringExtra("Recipename");
        recipeStepNumber = getIntent().getIntExtra("RecipeStepPosition" , 0);
        mStepList = getIntent().getParcelableArrayListExtra("RecipeSteps");

        getSupportActionBar().setTitle(recipeName);

        setupRecipeStepUI(recipeStepNumber);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipeStepNumber<mStepList.size()-1){
                    recipeStepNumber = recipeStepNumber+1;
                    setupRecipeStepUI(recipeStepNumber);
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipeStepNumber>0 ){
                    recipeStepNumber = recipeStepNumber-1;
                    setupRecipeStepUI(recipeStepNumber);
                }
            }
        });
    }

    private void setupRecipeStepUI(int recipeStepNumber){
        String mRecipeStepDescription = mStepList.get(recipeStepNumber).getShortDescription()+"\n\n\n"+mStepList.get(recipeStepNumber).getDescription();
        int totalRecipeSteps = mStepList.size()-1;
        String recipeStepNumberDisplay = recipeStepNumber+"/"+totalRecipeSteps;
        mStepDescriptionTextView.setText(mRecipeStepDescription);
        mStepNumberTextView.setText(recipeStepNumberDisplay);
    }

}
