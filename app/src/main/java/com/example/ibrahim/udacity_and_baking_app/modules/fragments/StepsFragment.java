package com.example.ibrahim.udacity_and_baking_app.modules.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.google.android.exoplayer2.C;
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

/**
 *Created by ibrahim on 01/06/18.
 */

@SuppressWarnings("WeakerAccess")
public class StepsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StepsFragment";
    @SuppressWarnings("unused")
    private static MediaSessionCompat mMediaSession;
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
    @BindView(R.id.image_alternative)
    protected ImageView mImageAlternative;

    private boolean mRotation;
    private SimpleExoPlayer mExoPlayer;
    private long mPlayerPosition;
    private boolean isPlayWhenReady;
    private int mIndex;
    private Boolean mIstablet;
    private Boolean mNoRotation;
    private ArrayList<Steps> mStepsArrayList = new ArrayList<>();
    private Unbinder unbinder;

    //method get value saved in bundle which coming from DetailsActivity
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //create this to continue play video after saveState(after rotate device)
        if (savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong(Contract.EXTRA_PLAYER_POSITION);
            isPlayWhenReady = savedInstanceState.getBoolean(Contract.EXTRA_PLAYER_READY);
            if (mPlayerPosition != C.TIME_UNSET && isPlayWhenReady) {
                Log.d(TAG, "mPlayerPosition onRestore = " + String.valueOf(mPlayerPosition));
                mExoPlayer.seekTo(mPlayerPosition);

            }
            Log.d(TAG, "mPlayerPosition after savedInstanceState = " + String.valueOf(mPlayerPosition));
            Log.d(TAG, "isPlayWhenReady after savedInstanceState = " + String.valueOf(isPlayWhenReady));

        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        unbinder = ButterKnife.bind(this, view);

        //get bundle that coming from activity that responsible of creating fragment
        Bundle extras = this.getArguments();
        //to make sure that bundle not null
        if (extras != null) {
            //pass extras to read Bundle method get value of bundle
            readBundle(extras);
            Log.d(TAG, " mIndex after getArguments = " + mIndex);
            Log.d(TAG, "mRotation after getArguments = " + String.valueOf(mRotation));
            Log.d(TAG, "isPlayWhenReady before savedInstanceState = " + String.valueOf(isPlayWhenReady));
            Log.d(TAG, "mPlayerPosition before savedInstanceState = " + String.valueOf(mPlayerPosition));


            //Boolean mRotation has value coming from bundle if  device rotate it is true
            if (mRotation) {
                //make viewGroup that contains exoplayer match parent
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                //Also cancel padding make it 0 in all edges
                mLinearLayout.setPadding(0, 0, 0, 0);
                mLinearLayout.setLayoutParams(layoutParams);
                mRelativeLayout.setVisibility(View.GONE);

            }
        }


        //play player if step list not empty
        if (mStepsArrayList != null && mStepsArrayList.size() > 0) {
            showView();
        }

        //running onclick for change video
        mImgMoveLeft.setOnClickListener(this);
        mImgMoveright.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        releasePlayer();
    }

    /**
     * method to play video pass
     *
     * @param mVideoUri by get mIndex from onClick inside fragment
     *                  by it,s position will get value
     */

    private void initializePlayer(Uri mVideoUri) {

        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(true);
            MediaSource mediaSource = buildMediaSource(mVideoUri);
            mExoPlayer.prepare(mediaSource, false, false);

        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //last position of playing video
        outState.putLong(Contract.EXTRA_PLAYER_POSITION, mPlayerPosition);
        //last state of player if ready(true) or not ready(false)
        outState.putBoolean(Contract.EXTRA_PLAYER_READY, isPlayWhenReady);

        Log.d(TAG, "mPlayerPosition before savedInstanceState = " + String.valueOf(mPlayerPosition));
        Log.d(TAG, "isPlayWhenReady before savedInstanceState = " + String.valueOf(isPlayWhenReady));

    }

    //enabling functionality such  looping and playback of sequences of videos
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("Baking_app"),
                new DefaultExtractorsFactory(), null, null);
    }

    //make player empty
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;

        }
    }


    @Override
    public void onPause() {
        super.onPause();
        //get current position for video playing  before onPause
        mPlayerPosition = mExoPlayer.getCurrentPosition();
        //get current ready state of video player before onPause
        isPlayWhenReady = mExoPlayer.getPlayWhenReady();

        Log.d(TAG, "mPlayerPosition onPause = " + String.valueOf(mPlayerPosition));

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

        /* in onclick change value of int mIndex and
        *  get the values from Steps ArrayList depending on of the position that equal mIndex
        *  in onclick changing value of mIndex*/
        int id = view.getId();
        if (id == R.id.move_left) {
            //mImgMoveLeft will increase mIndex 1 every one click
            if (mStepsArrayList != null) {
                if (mIndex == mStepsArrayList.size())
                    return;
                mIndex++;
                //Hide or show the image by accessing the last position in the lis
                if (mIndex >= mStepsArrayList.size()) {
                    mIndex = mStepsArrayList.size() - 1;
                    mImgMoveLeft.setVisibility(View.INVISIBLE);
                    mImgMoveright.setVisibility(View.VISIBLE);
                } else {
                    mImgMoveright.setVisibility(View.VISIBLE);
                }
                Log.d(TAG, "mIndex move_left = " + mIndex);
                //save the value of mIndex in SharedPreferences to use it in activity
                SharedPrefManager.getInstance(getActivity()).setPrefIndex(mIndex);
                showView();
            }

        } else if
                (id == R.id.move_right) {
            //mImgMoveright will decrease mIndex 1 every one click
            if (mStepsArrayList != null) {
                if (mIndex == 0)
                    return;
                mIndex--;
                //Hide or show the image by accessing the first position in the list
                if (mIndex <= 0) {
                    mImgMoveright.setVisibility(View.INVISIBLE);
                    mImgMoveLeft.setVisibility(View.VISIBLE);
                } else {
                    mImgMoveLeft.setVisibility(View.VISIBLE);
                }
                Log.d(TAG, "mIndex move_right = " + mIndex);
                //save the value of mIndex in SharedPreferences to use it in activity
                SharedPrefManager.getInstance(getActivity()).setPrefIndex(mIndex);
            }
            showView();
        }
    }

    //Responsible for controlling the overview for fragment
    private void showView() {
        releasePlayer();
        mImageAlternative.setVisibility(View.GONE);
        //make sure mStepsArrayList not empty
        if (mStepsArrayList.size() > 0) {
            //get value of videoUrl by pass last value of mIndex
            String videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
            //get value of thumbnailURL by pass last value of mIndex
            String thumbnailURL = mStepsArrayList.get(mIndex).getThumbnailURL();
            /*if boolean mNoRotation or mIstablet true will show the Description below video
            * no rotation because no space in screen to display will show Description just in Tablet*/
            if (mNoRotation || mIstablet) {
                mTxtDescription.setText(mStepsArrayList.get(mIndex).getDescription());

            }
            //play video if value of videoUrl not empty if empty just text will display message
            if (videoUrl.isEmpty() && thumbnailURL.isEmpty()) {

                mTextNoVideo.setVisibility(View.VISIBLE);
                mPlayerView.setVisibility(View.GONE);
                mImageAlternative.setVisibility(View.GONE);
                Log.d(TAG, "TestShow \n Description = " + mStepsArrayList.get(mIndex).getDescription());


            } else if (!videoUrl.isEmpty()) {
                initializePlayer(Uri.parse(videoUrl));
                mTextNoVideo.setVisibility(View.GONE);
                mPlayerView.setVisibility(View.VISIBLE);
                Log.d(TAG, "TestShow  \n videoUrl =" + videoUrl);

            } else if
                    (!thumbnailURL.isEmpty()) {
                mTextNoVideo.setVisibility(View.GONE);
                mPlayerView.setVisibility(View.GONE);
                mImageAlternative.setVisibility(View.VISIBLE);

                Glide.with(getActivity())
                        .load(thumbnailURL)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.brownies))
                        .into(mImageAlternative);

                Log.d(TAG, "TestShow \n thumbnailURL =" + thumbnailURL);

            }


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
}

