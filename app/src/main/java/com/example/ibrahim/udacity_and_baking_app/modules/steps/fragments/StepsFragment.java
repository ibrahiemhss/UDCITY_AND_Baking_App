package com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

;
import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseFragments;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerFragmentStepComponent;
import com.example.ibrahim.udacity_and_baking_app.di.module.FragmentStepModule;
import com.example.ibrahim.udacity_and_baking_app.modules.details.DetailsActivity;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.AdpaterListener;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.StepsAdapter;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.StepfragmentPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.StepsView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.MAX_BUFFER_DURATION;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.MIN_BUFFER_DURATION;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.MIN_PLAYBACK_RESUME_BUFFER;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.MIN_PLAYBACK_START_BUFFER;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.VIDEO_URL;

/**
 * Created by ibrahim on 01/06/18.
 */

public class StepsFragment extends BaseFragments implements StepsView, ExoPlayer.EventListener,MyStringListener/* , Player.EventListener*/ {
    private static final String TAG = "StepsFragment";
    public static final String EXTRA_STEP_POSITION = "extra_step_position";

    @Override
    public void onStepsLoaded(ArrayList<Steps> stepsList) {
        stepsArrayList = stepsList;
    }


    @BindView(R.id.playerView)
    protected SimpleExoPlayerView mPlayerView;

    @BindView(R.id.fullscreen_content)
    protected FrameLayout mFrameLayout;

    private SimpleExoPlayer mExoPlayer;

    private String videoUrl;
    private int position;


    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;


    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;


    List<Steps> stepsArrayList = new ArrayList<>();




    private String mVideoUrl;
    private String mDescription;


    @BindView(R.id.tv_descriptoin)
    protected TextView mTxtDescription;


    Unbinder unbinder;


    @Inject
    protected StepfragmentPresenter mPresenter;


    public StepsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependency();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_steps, container, false);
//             position = getArguments().getInt("uuu");

        position = SharedPrefManager.getInstance(getContext()).getPrefPosition();
        unbinder = ButterKnife.bind(this, view);
        mPresenter.getSteps(position);
        Log.d("mypossssssssss3", String.valueOf(SharedPrefManager.getInstance(getContext()).getPrefPosition()));
    /*  if(SharedPrefManager.getInstance(getActivity())!=null){
          initializePlayer(SharedPrefManager.getInstance(getActivity()).getVideoUrl());

      }*/
     //   mVideoUrl="https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffde28_1-mix-all-dry-ingredients-yellow-cake/1-mix-all-dry-ingredients-yellow-cake.mp4";
        initializePlayer();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }


    @Override
    public StepsView getSeps() {
        return null;
    }

    @Override
    protected void resolveDaggerDependency() {

        DaggerFragmentStepComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fragmentStepModule(new FragmentStepModule(this))
                .build().injectStepsFragment(this);
    }



    @Override
    public void resume() {
        // mExoPlayer.setPlayWhenReady(true);
        super.onResume();
    }

    @Override
    public void pause() {
        // mExoPlayer.setPlayWhenReady(false);
        super.onPause();
    }

    @Override
    public void destroy() {
        //  mExoPlayer.release();
        super.onDestroy();
    }



    public void initializePlayer() {



            if (mExoPlayer == null) {
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);
                mExoPlayer.addListener(this);

                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(getActivity(), "Baking_app");
                MediaSource mediaSource = new ExtractorMediaSource(
                        Uri.parse(
                                SharedPrefManager.getInstance(getActivity()).getVideoUrl()
                        )
                        , new DefaultDataSourceFactory(
                        getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
                Log.d(TAG, "VideoURL: inside player" + SharedPrefManager.getInstance(getActivity()).getVideoUrl());




        }

    }

    public void setVideoView(String mVideoUrl) {
        if (mVideoUrl != null) {
            //  videoView.setVideoURI(Uri.parse(mVideoUrl));
            Log.d(TAG, "VideoURL:" + mVideoUrl);
            // videoView.setVideoPath("https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4");

        } else {
        }
    }

    private void releasePlayer() {
//        mNotificationManager.cancelAll();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {

            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            Log.d(TAG, "onPlayerStateChanged: PLAYING");
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            Log.d(TAG, "onPlayerStateChanged: PAUSED");
        }
//        mMediaSession.setPlaybackState(mStateBuilder.build());
        //showNotification(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void computeSomething(String myString) {
        Log.d(TAG, "VideoURL: listner" + myString);

        initializePlayer();
    }


    /**
     * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
     */
    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }

}

