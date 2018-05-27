package com.example.ibrahim.udacity_and_baking_app.modules.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by ibrahim on 25/05/18.
 */


public class BakesAdapter extends RecyclerView.Adapter<BakesAdapter.Holder> {



    private LayoutInflater mLayoutInflater;
    private List<Bake> mBakeList=new ArrayList<>();
    public BakesAdapter(LayoutInflater inflater){

        mLayoutInflater=inflater;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= mLayoutInflater.inflate(R.layout.list_item_bakes,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.bind(mBakeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mBakeList.size();
    }
    public void addBakes(List<Bake> bakes){
        mBakeList.addAll(bakes);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.bake_icon) protected ImageView mBakeseIcon;
        @BindView(R.id.textview_name) protected TextView mCakeTitle;
        private Context mContext;

        public Holder(View itemView) {
            super(itemView);
            mContext=itemView.getContext();
            ButterKnife.bind(this,itemView);
        }

        public void bind(Bake bake) {
        }
    }
}

