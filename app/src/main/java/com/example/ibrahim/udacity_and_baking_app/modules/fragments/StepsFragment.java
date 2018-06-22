package com.example.ibrahim.udacity_and_baking_app.modules.fragments;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.StepsAdapter;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 *Created by ibrahim on 01/06/18.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class StepsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StepsFragment";
    private static MediaSessionCompat mMediaSession;
    private final Handler mHideHandler = new Handler();
    @BindView(R.id.tv_descriptoin)
    protected TextView mTxtDescription;
    @BindView(R.id.tv_txt_novid)
    protected TextView mTextNoVideo;
    @BindView(R.id.move_left)
    protected ImageView mImgMoveLeft;
    @BindView(R.id.move_right)
    protected ImageView mImgMoveright;
    @BindView(R.id.playerView)
    protected SimpleExoPlayerView mPlayerView;
    @BindView(R.id.fullscreen_content)
    protected LinearLayout mLinearLayout;
    @BindView(R.id.rv_details)
    protected RelativeLayout mRelativeLayout;

    private StepsAdapter.OnStepsClickListener mOnStepsClickListener;
    private boolean mRotation;
    private Bundle savedState = null;
    private SimpleExoPlayer mExoPlayer;
    private int mIndex;
    private Boolean mIstablet;
    private Boolean mNoRotation;
    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;
    private View mContentView;
    private ArrayList<Steps> mStepsArrayList = new ArrayList<>();
    private String mVideoUrl;
    private String mDescription;
    private Unbinder unbinder;
    private ViewGroup container;
    private LayoutInflater inflater;


    private void readBundle(Bundle bundle) {
        if (bundle != null && bundle.containsKey(Contract.EXTRA_STATE_STEPS) && bundle.containsKey(Contract.EXTRA_STEP_INDEX)) {
            mStepsArrayList = bundle.getParcelableArrayList(Contract.EXTRA_STATE_STEPS);
            mIndex = bundle.getInt(Contract.EXTRA_STEP_INDEX);
            mRotation = bundle.getBoolean(Contract.EXTRA_ROTATION);
            mIstablet = bundle.getBoolean(Contract.EXTRA_IS_TABLET);
            mNoRotation = bundle.getBoolean(Contract.EXTRA_NO_ROTATION);

            if (mStepsArrayList != null) {
                Log.d(TAG, "bundleList received= " + String.valueOf(mStepsArrayList.size()));

            }
            Log.d(TAG, "mIndex savedInstanceStateFragment = " + mIndex);
            Log.d(TAG, "mRotation savedInstanceStateFragment = " + String.valueOf(mRotation));

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        unbinder = ButterKnife.bind(this, view);
        Bundle extras = this.getArguments();

        if (extras != null) {
            readBundle(extras);
            Log.d(TAG, " mIndex after getArguments = " + mIndex);
            Log.d(TAG, "mRotation after getArguments = " + String.valueOf(mRotation));

            if (mRotation) {
                unbinder = ButterKnife.bind(this, view);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                mLinearLayout.setPadding(0, 0, 0, 0);
                mLinearLayout.setLayoutParams(layoutParams);


                mRelativeLayout.setVisibility(View.GONE);


            } else {
                mLinearLayout = new LinearLayout(getActivity());
                mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 250));
                mRelativeLayout.setVisibility(View.VISIBLE);

            }
        }

        if (mStepsArrayList != null && mStepsArrayList.size() > 0) {
            show();
        }
        // mPresenter.getSteps(position);


        if (mIndex == 0) {
            mImgMoveright.setVisibility(View.INVISIBLE);
        }
        if (mIndex == mStepsArrayList.size()) {
            mImgMoveLeft.setVisibility(View.INVISIBLE);
        }
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


    private void initializePlayer(Uri mVideoUri) {


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
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.move_left) {


            if (mStepsArrayList != null) {
                if (mIndex == mStepsArrayList.size())
                    return;
                mIndex++;

                if (mIndex >= mStepsArrayList.size()) {
                    mIndex = mStepsArrayList.size() - 1;
                    mImgMoveLeft.setVisibility(View.INVISIBLE);
                    mImgMoveright.setVisibility(View.VISIBLE);

                } else {
                    mImgMoveright.setVisibility(View.VISIBLE);

                }
                Log.d(TAG, "mIndex move_left = " + mIndex);
                SharedPrefManager.getInstance(getActivity()).setPrefIndex(mIndex);

                show();
            }
        } else if (id == R.id.move_right) {


            if (mStepsArrayList != null) {

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
                SharedPrefManager.getInstance(getActivity()).setPrefIndex(mIndex);

            }
            show();
        }


    }

    private void show() {

        releasePlayer();

        if (mStepsArrayList.size() > 0) {

            String videoUrl = getVideoUrl(mIndex);
            if (mNoRotation || mIstablet) {
                mTxtDescription.setText(mStepsArrayList.get(mIndex).getDescription());

            }


            if (!videoUrl.isEmpty()) {

                initializePlayer(Uri.parse(videoUrl));

                mTextNoVideo.setVisibility(View.GONE);

                mPlayerView.setVisibility(View.VISIBLE);
            } else {
                mTextNoVideo.setVisibility(View.VISIBLE);

                mPlayerView.setVisibility(View.GONE);
            }

            Log.d(TAG, "VideoURL:show" + videoUrl);

        }

    }

    private String getVideoUrl(int index) {
        String videoStream;
        if (mStepsArrayList.get(index).getVideoURL().isEmpty()) {
            videoStream = mStepsArrayList.get(index).getThumbnailURL();
        } else {
            videoStream = mStepsArrayList.get(index).getVideoURL();
        }
        return videoStream;
    }

    public View initializeUi() {
        View view = null;

        int orientation = getActivity().getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view = inflater.inflate(R.layout.fragment_steps, container, false);
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            view = inflater.inflate(R.layout.fragment_steps, container, false);
        }
        return view;
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
