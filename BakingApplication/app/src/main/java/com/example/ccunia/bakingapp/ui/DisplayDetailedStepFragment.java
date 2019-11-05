package com.example.ccunia.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccunia.bakingapp.R;
import com.example.ccunia.bakingapp.data.IngredientList;

import com.example.ccunia.bakingapp.data.StepsRecipe;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CCunia on 7/19/2018.
 */

public class DisplayDetailedStepFragment extends Fragment implements Player.EventListener {

    private final String TAG = DisplayDetailedStepFragment.class.getSimpleName();
    public static final String APP_NAME = "BakingApplication";
    public static final String CURRENT_VIDEO_POSITION = "position";
    public static final String CURRENT_TITLE="title";
    public static final String STATE_TAG="state";
    public static ArrayList<StepsRecipe> mSteps;
    public static int mPosition;
    public static String mDescription, mVideoUrl;
    private SimpleExoPlayer recipeExoPlayer;
    private long exoPosition;
    private boolean mState = true;
    @BindView(R.id.ib_previous) ImageButton previousStep;
    @BindView(R.id.ib_next) ImageButton nextStep;
    @BindView(R.id.iv_play_video) PlayerView loadVideo;
    @BindView(R.id.tv_detailed_step) TextView mTVDescription;


    public DisplayDetailedStepFragment() {
    }

    public static DisplayDetailedStepFragment newInstance(ArrayList<StepsRecipe> steps, int position){

        DisplayDetailedStepFragment stepsFragment = new DisplayDetailedStepFragment();
        mSteps = steps;
        mPosition = position;
        return stepsFragment;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState !=  null && recipeExoPlayer != null){

            Long lastPosition = savedInstanceState.getLong(CURRENT_VIDEO_POSITION);
            recipeExoPlayer.seekTo(lastPosition);
            getActivity().setTitle(savedInstanceState.getString(CURRENT_TITLE));
            mState = savedInstanceState.getBoolean(STATE_TAG);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_display_step_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean tabletMode = ((MainActivity) getActivity()).isTablet();
        if(tabletMode){
            previousStep.setVisibility(View.GONE);
            nextStep.setVisibility(View.GONE);
        }

        onNextStep(view);
        onPreviousStep(view);

        updateStepInformation();
        settingStepInformation();

    }

    public void updateStepInformation(){
        //getting values
        mDescription = mSteps.get(mPosition).getDescription();
        mVideoUrl = mSteps.get(mPosition).getVideoUrl();
    }

    public void settingStepInformation(){
        //settting steps
        if (mVideoUrl.isEmpty()) {
            loadVideo.setVisibility(View.INVISIBLE);
            recipeExoPlayer = null;

        }else{
            initializePlayer(Uri.parse(mVideoUrl));
        }

        mTVDescription.setText(mDescription);
    }
    public void onPreviousStep(View view){
        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition--;
                if (mPosition > 0) {
                    updateStepInformation();
                    settingStepInformation();

                }else{
                    mPosition = 0;
                }

            }
        });
    }

    public void onNextStep(View view){
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPosition++;
                if (mPosition<mSteps.size()) {
                    updateStepInformation();
                    settingStepInformation();

                }else   {
                    mPosition = mSteps.size()-1;
                }

            }
        });
    }

    private void initializePlayer(Uri uri) {
        if (recipeExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            recipeExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            loadVideo.setVisibility(View.VISIBLE);
            loadVideo.setPlayer(recipeExoPlayer);
        } else {
            recipeExoPlayer.stop();
        }
        String userAgent = Util.getUserAgent(getContext(), APP_NAME);
        MediaSource mediaSource = new LoopingMediaSource(
                new ExtractorMediaSource
                        .Factory(new DefaultDataSourceFactory(getContext(), userAgent))
                        .createMediaSource(uri));

        recipeExoPlayer.prepare(mediaSource);
        recipeExoPlayer.setPlayWhenReady(mState);
        recipeExoPlayer.addListener(this);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CURRENT_TITLE, getActivity().getTitle().toString());
        outState.putLong(CURRENT_VIDEO_POSITION, exoPosition);
        outState.putBoolean(STATE_TAG, mState);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (recipeExoPlayer != null) {
            recipeExoPlayer.stop();
            recipeExoPlayer = null;
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (recipeExoPlayer != null){
            recipeExoPlayer.stop();
            recipeExoPlayer.release();
            recipeExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (recipeExoPlayer!=null){
            exoPosition = recipeExoPlayer.getContentPosition();
            mState = recipeExoPlayer.getPlayWhenReady();
            recipeExoPlayer.stop();

        }

    }

    @Override
    public void onResume() {
       super.onResume();
        if (recipeExoPlayer != null) {
            loadVideo.setPlayer(recipeExoPlayer);
            recipeExoPlayer.setPlayWhenReady(true);
        }
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

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }
}
