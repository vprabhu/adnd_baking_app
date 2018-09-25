package com.vhp.baking;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.vhp.baking.model.Step;
import com.vhp.baking.ui.RecipePlayFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipePlayActivity extends AppCompatActivity {

    private static final String TAG = RecipePlayActivity.class.getSimpleName();
    private ArrayList<Step> mStepList;
    private int recipeStepNumber;
    private SimpleExoPlayer player;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_play);
        ButterKnife.bind(RecipePlayActivity.this);

        String recipeName = getIntent().getStringExtra("Recipename");
        recipeStepNumber = getIntent().getIntExtra("RecipeStepPosition" , 0);
        mStepList = getIntent().getParcelableArrayListExtra("RecipeSteps");

        getSupportActionBar().setTitle(recipeName);



        RecipePlayFragment mRecipePlayFragment = new RecipePlayFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt("RecipeStepPosition" , recipeStepNumber);
        mBundle.putParcelableArrayList("RecipeSteps" , (ArrayList<? extends Parcelable>) mStepList);
        mRecipePlayFragment.setArguments(mBundle);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.layout_recipe_container , mRecipePlayFragment
        ).commit();

    }



}
