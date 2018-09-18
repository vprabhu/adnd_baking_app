package com.vhp.baking;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipePlayActivity extends AppCompatActivity implements ExoPlayer.EventListener {

    private static final String TAG = RecipePlayActivity.class.getSimpleName();
    @BindView(R.id.button_next)
    Button mNextButton;
    @BindView(R.id.button_prev)
    Button mPrevButton;
    @BindView(R.id.textView_step_info)
    TextView mStepNumberTextView;
    @BindView(R.id.textView_recipe_steps_expanded)
    TextView mStepDescriptionTextView;
    @BindView(R.id.imageView_recipe_row_image)
    PlayerView mPlayerView;
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
        // setting the default artwork on PlayerView
        mPlayerView.setDefaultArtwork(
                BitmapFactory.decodeResource(
                        getResources() ,
                        R.mipmap.ic_launcher_round));
        initializePlayer();
        player.addListener(RecipePlayActivity.this);

        setupRecipeStepUI(recipeStepNumber);
    }


    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setPlayer(player);

        Uri uri = Uri.parse(mStepList.get(recipeStepNumber).getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void changeTrack(){
        Uri uri = Uri.parse(mStepList.get(recipeStepNumber).getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void setupRecipeStepUI(int recipeStepNumber){
        String mRecipeStepDescription = mStepList.get(recipeStepNumber).getShortDescription()+"\n\n\n"+mStepList.get(recipeStepNumber).getDescription();
        int totalRecipeSteps = mStepList.size()-1;
        String recipeStepNumberDisplay = recipeStepNumber+"/"+totalRecipeSteps;
        mStepDescriptionTextView.setText(mRecipeStepDescription);
        mStepNumberTextView.setText(recipeStepNumberDisplay);
        changeTrack();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if(playbackState == Player.STATE_READY && playWhenReady){
            Log.d(TAG, "onPlayerStateChanged: playing");
        }else if(playbackState == Player.STATE_READY){
            Log.d(TAG, "onPlayerStateChanged: pause mode");
        }else if(playbackState == Player.STATE_IDLE){
            Log.d(TAG, "onPlayerStateChanged: Idle");
            // setting the default artwork on PlayerView
            mPlayerView.hideController();
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
