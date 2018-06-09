package com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
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

public class StepsFragment extends Fragment implements StepsView, View.OnClickListener/* , Player.EventListener*/ {
    public static final String TAG = "StepsFragment";
    public static final String EXTRA_STEP_POSITION = "extra_step_position";
    public static final String EXTRA_STEP_INDEX = "extra_index";
    public static final String EXTRA_STEP_LIST = "extra_steps_list";
    public static final String EXTRA_STEP_LIST_ACTIVITY = "extra_steps_list_avtivity";
    public static final String EXTRA_LARG_SCREEN = "extra_larg";


    @Override
    public void onStepsLoaded(ArrayList<Steps> stepsList) {
      //  mStepsArrayList = stepsList;
    }

    private Bundle savedState = null;

    @BindView(R.id.tv_descriptoin) protected TextView mTxtDescription;
    @BindView(R.id.tv_txt_novid) protected TextView mTextNoVideo;
    @BindView(R.id.move_left) protected ImageView mImgMoveLeft;
    @BindView(R.id.move_right) protected ImageView mImgMoveright;

    @BindView(R.id.playerView) protected SimpleExoPlayerView mPlayerView;

    @BindView(R.id.fullscreen_content) protected FrameLayout mFrameLayout;

    protected SimpleExoPlayer mExoPlayer;

    private int position;
    private int mIndex;
    private Boolean mIsLarg;
    boolean savStat;

    public int getmIndex() {
        return mIndex;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;


    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;


    private ArrayList<Steps> mStepsArrayList = new ArrayList<>();


    public static StepsFragment newInstance(ArrayList<Steps> stepsArrayList, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_STEP_LIST_ACTIVITY, stepsArrayList);
        bundle.putInt(EXTRA_STEP_POSITION, position);

        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

  private void readBundle(Bundle bundle) {
      if (bundle != null && bundle.containsKey(EXTRA_STEP_LIST_ACTIVITY) && bundle.containsKey(EXTRA_STEP_POSITION)
              &&bundle.getParcelableArray(EXTRA_STEP_LIST_ACTIVITY)!=null) {
          mStepsArrayList = bundle.getParcelableArrayList(EXTRA_STEP_LIST_ACTIVITY);
          position = bundle.getInt(EXTRA_STEP_POSITION);
          mIndex = SharedPrefManager.getInstance(getActivity()).getPrefPosition();
          Log.d(TAG, "bundleList 1 = " + mStepsArrayList.size());
          Log.d(TAG, "mIndex 1 = " + mIndex);
          Log.d(TAG, "mIndex id  = " + mStepsArrayList.get(mIndex).getId());

      }

  }


    private String mVideoUrl;
    private String mDescription;



    Unbinder unbinder;


    @Inject
    protected StepfragmentPresenter mPresenter;


    public StepsFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);





    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        unbinder = ButterKnife.bind(this, view);

        Bundle extras = this.getArguments();

        if(savedInstanceState==null){
            if (extras != null) {
                mStepsArrayList = extras.getParcelableArrayList(EXTRA_STEP_LIST_ACTIVITY);
                mIndex=extras.getInt(EXTRA_STEP_INDEX);
                mIsLarg=extras.getBoolean(EXTRA_LARG_SCREEN);
                Log.d(TAG, " mIndex getArguments = " + mIndex);

            }
        }
        if (savedInstanceState != null) {
            mStepsArrayList=savedInstanceState.getParcelableArrayList(EXTRA_STEP_LIST);
            mIndex=SharedPrefManager.getInstance(getActivity()).getPrefPosition();
            Log.d(TAG, " mIndex savedInstanceState = " + mIndex);



        }



        if(mStepsArrayList!=null&&mStepsArrayList.size()>0) {
            show();
        }
       // mPresenter.getSteps(position);


        mImgMoveLeft.setOnClickListener(this);
        mImgMoveright.setOnClickListener(this);

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



    public void initializePlayer(Uri mVideoUri) {



        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(true);
            MediaSource mediaSource = buildMediaSource(mVideoUri);
            mExoPlayer.prepare(mediaSource, true, false);
        }
    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("Baking_app"),
                new DefaultExtractorsFactory(), null, null);
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        int id =view.getId();
        if (id == R.id.move_left) {
            mIndex++;
            if (mIndex >= mStepsArrayList.size()) {
                mIndex = mStepsArrayList.size() - 1;
                mImgMoveLeft.setVisibility(View.INVISIBLE);
                mImgMoveright.setVisibility(View.VISIBLE);

            } else {
                mImgMoveright.setVisibility(View.VISIBLE);

            }
            Log.d(TAG, "mIndex move_left = " + mIndex);

            show();
        } else if (id == R.id.move_right) {
            if (mIndex == 0)
                return;
            mIndex--;
            if (mIndex <= 0) {
                mImgMoveright.setVisibility(View.INVISIBLE);
                mImgMoveLeft.setVisibility(View.VISIBLE);

            } else {
                mImgMoveLeft.setVisibility(View.VISIBLE);

            }
            Log.d(TAG, "mIndex move_right = " + mIndex);

            show();
        }



    }
    public void show() {

        releasePlayer();

        if(mStepsArrayList.size()>0){

            String videoUrl = mStepsArrayList.get(getmIndex()).getVideoURL();
            if(mIsLarg){
              //  mTxtDescription.setText(mStepsArrayList.get(mIndex).getDescription());

            }

            if (!videoUrl.isEmpty()) {

                initializePlayer(Uri.parse(videoUrl));

                mTextNoVideo.setVisibility(View.GONE);

                mPlayerView.setVisibility(View.VISIBLE);
            }else {
                mTextNoVideo.setVisibility(View.VISIBLE);

                mPlayerView.setVisibility(View.GONE);
            }

            Log.d(TAG, "VideoURL:show" + videoUrl);

        }

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
    @Override
    public void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_STEP_INDEX, mIndex);
        SharedPrefManager.getInstance(getActivity()).setPrefPosition(mIndex);
        outState.putParcelableArrayList(EXTRA_STEP_LIST,mStepsArrayList);
        Log.d(TAG, "mIndex outState = " + getmIndex());
        savStat=true;
    }



}

