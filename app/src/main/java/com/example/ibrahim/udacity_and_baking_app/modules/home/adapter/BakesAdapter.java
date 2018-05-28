package com.example.ibrahim.udacity_and_baking_app.modules.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//TODO (74) create  BakesAdapter
/**
 *
 * Created by ibrahim on 25/05/18.
 */


public class BakesAdapter extends RecyclerView.Adapter<BakesAdapter.Holder> {

    private final Integer[] imgid;


    private final LayoutInflater mLayoutInflater;
    private final List<Bake> mBakeList=new ArrayList<>();
    public BakesAdapter(Integer[] imgid, LayoutInflater inflater){
        this.imgid = imgid;
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

        holder.bind(mBakeList.get(position),position);
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
        @BindView(R.id.bake_img) protected ImageView mBakeIcon;
        @BindView(R.id.textview_name) protected TextView mBakeName;
        private final Context mContext;

        public Holder(View itemView) {
            super(itemView);
            mContext=itemView.getContext();
            ButterKnife.bind(this,itemView);
        }

        public void bind(Bake bake,int position) {
            mBakeName.setText(bake.getName());
            mBakeIcon.setImageResource(imgid[position]);


        }
    }
}

