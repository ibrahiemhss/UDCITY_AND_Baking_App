package com.example.ibrahim.udacity_and_baking_app.modules.details.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by ibrahim on 01/06/18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.Holder> {



    private final LayoutInflater mLayoutInflater;
    private String mVideoURL;
    private String mDescription;

    private AdpaterListener mListener;


    private final List<Steps> mList=new ArrayList<>();
    public StepsAdapter( LayoutInflater inflater){
        mLayoutInflater=inflater;
    }

    @NonNull
    @Override
    public StepsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= mLayoutInflater.inflate(R.layout.list_item_steps,parent,false);

        return new StepsAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.Holder holder, int position) {

        holder.bind(mList.get(position),position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void addSteps(List<Steps> stepsList){
        mList.addAll(stepsList);
        notifyDataSetChanged();
    }
    @SuppressWarnings("WeakerAccess")
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.textview_id)
        protected TextView mId;
        @BindView(R.id.textview_shortDescription)
        protected TextView mShortDescription;

        private final Context mContext;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Steps steps, int position) {
            mId.setText(String.valueOf(steps.getId()));
            mShortDescription.setText(steps.getShortDescription());
            setVideoURL(steps.getVideoURL());
            setDescription(steps.getDescription());


        }
            @Override
            public void onClick (View view){
                if (mBakeClickListener != null) {
                    mBakeClickListener.onClick(getAdapterPosition());
                }

            }
        }

        public void setStepsClickListener(StepsAdapter.OnStepsClickListener listener) {
            mBakeClickListener = listener;
        }

        private StepsAdapter.OnStepsClickListener mBakeClickListener;

        public interface OnStepsClickListener {

            void onClick(int position);
        }

    public String getVideoURL() {
        return mVideoURL;
    }

    public void setVideoURL(String mVideoURL) {
        this.mVideoURL = mVideoURL;
    }
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }


    public void setAdapterListener(AdpaterListener listener) {
        mListener = listener;
    }
}
