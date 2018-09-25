package com.vhp.baking.ui;


import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
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
import com.vhp.baking.R;
import com.vhp.baking.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipePlayFragment extends Fragment implements Player.EventListener {

    private static final String TAG = RecipePlayFragment.class.getSimpleName();
    private Unbinder mUnbinder;
    private ArrayList<Step> mStepList;
    private int recipeStepNumber;
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
    private SimpleExoPlayer player;

    public RecipePlayFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipeStepNumber = bundle.getInt("RecipeStepPosition");
            mStepList = bundle.getParcelableArrayList("RecipeSteps");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_play, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

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
        player.addListener(this);
        setupRecipeStepUI(recipeStepNumber);
        return rootView;
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setPlayer(player);

        Uri uri = Uri.parse(mStepList.get(recipeStepNumber).getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("bakingapp")).
                createMediaSource(uri);
    }

    private void changeTrack(){
        Uri uri = Uri.parse(mStepList.get(recipeStepNumber).getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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
            if(mPlayerView!=null){
                mPlayerView.hideController();
            }
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
