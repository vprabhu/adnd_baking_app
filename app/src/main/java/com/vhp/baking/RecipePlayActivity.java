package com.vhp.baking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vhp.baking.model.Step;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_play);
        ButterKnife.bind(RecipePlayActivity.this);

        String recipeName = getIntent().getStringExtra("Recipename");
        ArrayList<Step> mStepList = getIntent().getParcelableArrayListExtra("RecipeSteps");

        getSupportActionBar().setTitle(recipeName);
        mStepDescriptionTextView.setText(mStepList.get(4).getDescription());
     }
}
